package com.appgro.service.pago;

import com.appgro.comun.EmailComponent;
import com.appgro.exception.NegocioException;
import com.appgro.model.entity.*;
import com.appgro.model.request.CorreoPlantillaRequest;
import com.appgro.model.response.CarritoResponse;
import com.appgro.model.response.CompraResponse;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.MercadoPagoResponse;
import com.appgro.repository.*;
import com.appgro.util.AppgroUtil;
import com.mercadopago.MercadoPago;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.mercadopago.resources.datastructures.preference.Phone;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.appgro.model.entity.AppCarritoCompra.*;
import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

@Service
public class MercadoPagoServiceImpl implements MercadoPagoService {

    private static final Logger LOGGER = Logger.getLogger(MercadoPagoServiceImpl.class);

    private final GenUsuarioRepository mUsuarioRepository;
    private final AppCarritoCompraRepository mAppCarritoCompraRepository;
    private final GenUsuarioTarifasRepository mGenUsuarioTarifasRepository;
    private final GenTiendaRepository mTiendaRepository;
    private final GenProductoRepository mProductoRepository;
    private final GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository;
    private final EmailComponent mEmailService;

    @Value("${access.token}")
    private String accessToken;

    @Value("${public.key}")
    private String publicKey;

	public MercadoPagoServiceImpl(GenUsuarioRepository mUsuarioRepository,
			AppCarritoCompraRepository mAppCarritoCompraRepository,
			GenUsuarioTarifasRepository mGenUsuarioTarifasRepository, GenTiendaRepository mTiendaRepository,
			GenProductoRepository mProductoRepository, GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository,
			EmailComponent mEmailService) {
        this.mUsuarioRepository = mUsuarioRepository;
        this.mAppCarritoCompraRepository = mAppCarritoCompraRepository;
        this.mGenUsuarioTarifasRepository = mGenUsuarioTarifasRepository;
        this.mTiendaRepository = mTiendaRepository;
        this.mProductoRepository = mProductoRepository;
        this.mGenUsuarioUbicacionRepository = mGenUsuarioUbicacionRepository;
        this.mEmailService = mEmailService;
    }

    /**
     * Metodo que hace el proceso de registro del pedido en mercadopago para que la app lo reciba
     *
     * @return Datos necesarios para continuar el pago
     */
    @Override
    public EntidadResponse<MercadoPagoResponse> realizarPago() {
        EntidadResponse<MercadoPagoResponse> respuesta = new EntidadResponse<>();
        try {
            //Configuracion del SDK de mercadopago
            MercadoPago.SDK.configure(accessToken);

            //Obtenemos el id usuario del JWT @NotNull
            Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

            if (idUsuario == null) {
                throw new NegocioException("No está autorizado para el consumo de este webservice");
            }

            //Lista de carritos en proceso para pagar
            List<CarritoResponse> productos =
                    mAppCarritoCompraRepository.consultarCarritoComprasByEstadoOrderByRegDateDesc(idUsuario, ESTADO_EN_PROCESO);

            //Lista de productos a pagar
            ArrayList<Item> itemsCompra = new ArrayList<>();
            if (productos != null && !productos.isEmpty()) {
                GenProducto mProducto = mProductoRepository.findFirstByIdProducto(productos.get(0).getIdProducto());
                GenUsuarioUbicacion ubicacionActual = mGenUsuarioUbicacionRepository.findFirstByIdUsuarioAndPredeterminado(idUsuario, true);
                GenTienda mGenTienda = this.mTiendaRepository.findById(mProducto.getIdTienda()).orElse(null);
                if (mGenTienda != null && mGenTienda.getLatitud() != null && mGenTienda.getLongitud() != null) {
                    Double distancia = AppgroUtil.distanciaEntreDosCoordenadas(ubicacionActual.getLatitud(),
                            ubicacionActual.getLongitud(), mGenTienda.getLatitud(), mGenTienda.getLongitud());
                    GenUsuarioTarifa tarifa = mGenUsuarioTarifasRepository.getTarifaByDistanciaAndProducto(distancia, productos.get(0).getIdProducto());
                    if (tarifa != null) {
                        Item item = new Item();
                        item.setId(String.valueOf(System.currentTimeMillis()))
                                .setTitle("Tarifa por distancia")
                                .setQuantity(1)
                                .setCategoryId("COP")
                                .setUnitPrice(tarifa.getTarifa().floatValue());
                        itemsCompra.add(item);
                    }
                }
                for (CarritoResponse carritoResponse : productos) {
                    Item item = new Item();
                    item.setId(String.valueOf(carritoResponse.getIdProducto()))
                            .setTitle(carritoResponse.getNombreProducto())
                            .setQuantity(carritoResponse.getCantidad())
                            .setCategoryId("COP")
                            .setUnitPrice(carritoResponse.getPrecioUnitario().floatValue());
                    itemsCompra.add(item);
                }
            }

            //Datos del pagador
            GenUsuario usuario = mUsuarioRepository.findFirstByIdUsuario(idUsuario);
            Payer payer = new Payer();
            payer.setEmail(usuario.getCorreo());
            payer.setName(usuario.getNombreCompleto());
            Phone telefono = new Phone();
            telefono.setNumber(usuario.getCelular());
            payer.setPhone(telefono);
            payer.setSurname(usuario.getPrimerApellido() + " " + usuario.getSegundoApellido());

            //Datos del pedido en mercadopago
            Preference preference = new Preference();
            preference.setPayer(payer);
            preference.setItems(itemsCompra);
            preference.save();

            MercadoPagoResponse mercadoPagoResponse = new MercadoPagoResponse();
            mercadoPagoResponse.setPublicKey(publicKey);
            mercadoPagoResponse.setPreferenceId(preference.getId());
            respuesta.setEntidad(mercadoPagoResponse);
            respuesta.setEstado(R0049);
        } catch (NegocioException ex) {
            LOGGER.error("Error mercadopago: " + ex, ex);
            respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
        } catch (Exception e) {
            LOGGER.error("Error mercadopago: " + e, e);
            respuesta.setEstado(exceptiontoEstadoResponse(e));
        }
        return respuesta;
    }

	/**
	 * Metodo que se invoca al momento de finalizar la transacción con mercadopago
	 *
	 * @param fechaEntrega fecha de entrega del pedido
	 */
	@Override
	public void recibirPago(Date fechaEntrega) {

		Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());
		List<AppCarritoCompra> productos = mAppCarritoCompraRepository.findAllByIdCompradorAndEstado(idUsuario,
				ESTADO_EN_PROCESO);
		final List<Long> idsCarrito = new ArrayList<>();
		if (productos != null && !productos.isEmpty()) {
			productos.forEach((AppCarritoCompra carrito) -> {
				carrito.setEstado(ESTADO_COMPRADO);
				carrito.setFechaEntrega(fechaEntrega);
				mAppCarritoCompraRepository.save(carrito);
				idsCarrito.add(carrito.getIdCarritoCompra());
			});
		}

		this.enviarCorreoCompraFinalizada(idsCarrito);
	}
    
	private void enviarCorreoCompraFinalizada(final List<Long> ids) {
		// Enviar correo:
		if (!CollectionUtils.isEmpty(ids)) {
			final List<CompraResponse> compras = this.mAppCarritoCompraRepository
					.consultarComprasByIdCompra(ids);
			
			String varFecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			String varTienda = compras.get(0).getNombreTienda();

			String varProductos = compras.stream()
					.map(c -> String.format("%s x%s", c.getNombreProducto(), c.getCantidad()))
					.collect(Collectors.joining(" | "));

			Double douTotal = compras.stream().mapToDouble(c -> c.getTotal()).sum();
			NumberFormat format = NumberFormat.getCurrencyInstance();
			format.setMaximumFractionDigits(0);
			String varTotal = format.format(douTotal);

			String varCliente = compras.get(0).getNombreComprador();
			String varDireccion = compras.get(0).getDireccion();
			String varCiudad = compras.get(0).getCiudad() + ", Colombia";

			// Creamos plantilla para envio de correo
			CorreoPlantillaRequest plantilla = new CorreoPlantillaRequest(AS_COMPRA_FINALIZADA, PL_COMPRA_FINALIZADA);
			plantilla.addPara(compras.get(0).getCorreoComprador());
			plantilla.addVariable("fecha", varFecha);
			plantilla.addVariable("tienda", varTienda);
			plantilla.addVariable("productos", varProductos);
			plantilla.addVariable("total", varTotal);
			plantilla.addVariable("cliente", varCliente);
			plantilla.addVariable("direccion", varDireccion);
			plantilla.addVariable("ciudad", varCiudad);

			plantilla.addVariable("location", "location");
			plantilla.addVariable("shopping", "shopping");

			plantilla.getInLine().put("location", new ClassPathResource("templates/images/location.png"));
			plantilla.getInLine().put("shopping", new ClassPathResource("templates/images/shopping-bag.png"));

			// Enviamos correo electrónico
			mEmailService.enviarCorreoPlantilla(plantilla);
		}
	}
}
