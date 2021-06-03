package com.appgro.service.aplicacion;

import com.appgro.exception.NegocioException;
import com.appgro.model.entity.AppCarritoCompra;
import com.appgro.model.entity.GenTienda;
import com.appgro.model.entity.GenUsuarioTarifa;
import com.appgro.model.entity.GenUsuarioUbicacion;
import com.appgro.model.request.RegistroCarritoRequest;
import com.appgro.model.response.CarritoResponse;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.ProductoResponse;
import com.appgro.repository.AppCarritoCompraRepository;
import com.appgro.repository.GenProductoRepository;
import com.appgro.repository.GenTiendaRepository;
import com.appgro.repository.GenUsuarioRepository;
import com.appgro.repository.GenUsuarioTarifasRepository;
import com.appgro.repository.GenUsuarioUbicacionRepository;
import com.appgro.util.AppgroUtil;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import static com.appgro.model.entity.AppCarritoCompra.ESTADO_COMPRADO;
import static com.appgro.model.entity.AppCarritoCompra.ESTADO_EN_PROCESO;
import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.AppgroUtil.getNombreUsuarioLogueado;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

@Service
public class CarritoCompraServiceImpl implements CarritoCompraService {

	private final GenUsuarioRepository mUsuarioRepository;
	private final AppCarritoCompraRepository mAppCarritoCompraRepository;
	private final GenProductoRepository mProductoRepository;
	private final GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository;
	private final GenTiendaRepository mTiendaRepository;
	private final GenUsuarioTarifasRepository mGenUsuarioTarifasRepository;


	public CarritoCompraServiceImpl(GenUsuarioRepository mUsuarioRepository,
			AppCarritoCompraRepository mAppCarritoCompraRepository, GenProductoRepository mProductoRepository,
			GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository, GenTiendaRepository mTiendaRepository,
			GenUsuarioTarifasRepository mGenUsuarioTarifasRepository) {
		this.mUsuarioRepository = mUsuarioRepository;
		this.mAppCarritoCompraRepository = mAppCarritoCompraRepository;
		this.mProductoRepository = mProductoRepository;
		this.mGenUsuarioUbicacionRepository = mGenUsuarioUbicacionRepository;
		this.mTiendaRepository = mTiendaRepository;
		this.mGenUsuarioTarifasRepository = mGenUsuarioTarifasRepository;
	}

	/**
	 * @see CarritoCompraService#guardarEnCarritoCompras(RegistroCarritoRequest)
	 */
	@Override
	@Transactional(rollbackOn = { RuntimeException.class })
	public EstadoResponse guardarEnCarritoCompras(RegistroCarritoRequest peticion) {
		try {

			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

			// Obtenemos el producto justo antes de guardar un registro en el carrito.
			ProductoResponse mProducto = mProductoRepository.findByIdProducto(peticion.getIdProducto());

			if (mProducto == null)
				return R0033;

			final GenUsuarioUbicacion ubicacionActual;
			GenTienda mGenTienda = null;

			// Asignamos el id de la tienda
			if (peticion.getIdUbicacion() == null) {
				ubicacionActual = mGenUsuarioUbicacionRepository.findFirstByIdUsuarioAndPredeterminado(idUsuario, true);
				peticion.setIdUbicacion(ubicacionActual.getIdUbicacion());
			}
			else {
				ubicacionActual = mGenUsuarioUbicacionRepository.findById(peticion.getIdUbicacion()).orElse(null);
			}

			if (ubicacionActual == null) {
				throw new NegocioException("No tiene ubicación seleccionada para el carrito.");
			}


			if(peticion.getIdProducto() != null) {
				mGenTienda = this.mTiendaRepository.findById(mProducto.getIdTienda()).orElse(null);
			}

			if( mGenTienda == null) {
				throw new NegocioException("No tiene tienda seleccionada para el carrito.");
			}

			// Asignamos el precio unitario al carrito:
			peticion.setPrecioUnitario(mProducto.getPrecio());

			// Asignamos costo de flete:
			Double distancia = AppgroUtil.distanciaEntreDosCoordenadas(ubicacionActual.getLatitud(),
                    ubicacionActual.getLongitud(), mGenTienda.getLatitud(), mGenTienda.getLongitud());

			Double costoFlete = this.obtenerCostoFlete(distancia, idUsuario);

			// Calculamos cantidad de unidades después de compra:
			Integer cantidadDespuesDeCompra = mProducto.getCantidadDisponible() - peticion.getCantidad();
			AppCarritoCompra mAppCarritoCompra = null;
			if (peticion.getIdCarritoCompra() != null) {
				mAppCarritoCompra = mAppCarritoCompraRepository.findById(peticion.getIdCarritoCompra()).orElse(null);
				if (mAppCarritoCompra == null) {
					return R0034;
				}
				cantidadDespuesDeCompra = mProducto.getCantidadDisponible() + mAppCarritoCompra.getCantidad()
						- peticion.getCantidad();
			}

			// Actualizamos el carrito de compras con la petición recibida:
			mAppCarritoCompra = new AppCarritoCompra(peticion, idUsuario);

			// Si cantidad de unidades es superior a cero, podemos guardar.
			if (cantidadDespuesDeCompra > 0) {
				// Agregamos producto/cantidad al carrito de compras:
				mAppCarritoCompra.setRegDate(new Date());
				mAppCarritoCompra.setFlete(costoFlete);

				mAppCarritoCompraRepository.save(mAppCarritoCompra);

				// Actualizamos disponibilidad:
				mProductoRepository.setDisponibilidadById(cantidadDespuesDeCompra, peticion.getIdProducto());

				return R0031;
			} else {
				return R0032;
			}

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}

	@Override
	public EntidadResponse<List<CarritoResponse>> obtenerCarritoComprasEnProceso() {
		EntidadResponse<List<CarritoResponse>> respuesta = new EntidadResponse<>();
		try {
			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());
			List<CarritoResponse> productos = mAppCarritoCompraRepository
					.consultarCarritoComprasByEstadoOrderByRegDateDesc(idUsuario, ESTADO_EN_PROCESO);
			respuesta.setEntidad(productos);
			respuesta.setEstado(R0035);
		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}

	@Override
	@Transactional(rollbackOn = { RuntimeException.class })
	public EstadoResponse eliminarDelCarritoCompras(Long idCarrito) {
		try {

			// Obtenemos el carrito de compra por su id
			AppCarritoCompra mAppCarritoCompra = mAppCarritoCompraRepository.findById(idCarrito).orElse(null);
			if (mAppCarritoCompra == null) {
				return R0037;
			}

			Integer disponibilidadACtual = mProductoRepository.getDisponibilidadById(mAppCarritoCompra.getIdProducto());
			Integer cantidadAntesDeCompra = disponibilidadACtual + mAppCarritoCompra.getCantidad();

			// Si encuentra el registro debemos eliminarlo.
			mAppCarritoCompraRepository.delete(mAppCarritoCompra);
			mProductoRepository.setDisponibilidadById(cantidadAntesDeCompra, mAppCarritoCompra.getIdProducto());

			return R0036;

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}

	@Override
	@Transactional(rollbackOn = { RuntimeException.class })
	public EstadoResponse eliminarCarritoComprasEnProceso() {
		try {
			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());
			List<AppCarritoCompra> listaCarritos = mAppCarritoCompraRepository.findAllByIdCompradorAndEstado(idUsuario,
					ESTADO_EN_PROCESO);
			for (AppCarritoCompra carritoCompra : listaCarritos) {

				Integer disponibilidadACtual = mProductoRepository.getDisponibilidadById(carritoCompra.getIdProducto());
				Integer cantidadAntesDeCompra = disponibilidadACtual + carritoCompra.getCantidad();

				mAppCarritoCompraRepository.delete(carritoCompra);
				mProductoRepository.setDisponibilidadById(cantidadAntesDeCompra, carritoCompra.getIdProducto());
			}

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
		return R0046;
	}

	@Override
	public EntidadResponse<List<CarritoResponse>> obtenerHistoricoCarrito() {
		EntidadResponse<List<CarritoResponse>> respuesta = new EntidadResponse<>();
		try {
			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());
			List<CarritoResponse> productos = mAppCarritoCompraRepository
					.consultarComprasByCompradorOrderByRegDateDesc(idUsuario);
			respuesta.setEntidad(productos);
			respuesta.setEstado(R0048);
		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}
	
	private Double obtenerCostoFlete(final Double doubleDistancia, final Long idUsuario) {
		List<GenUsuarioTarifa> tarifas = mGenUsuarioTarifasRepository.findByIdUsuarioOrderByRangoInicial(idUsuario);
		for (GenUsuarioTarifa tarifa : tarifas) {
			Double doubleRangoFinal = tarifa.getRangoFinal() == null ? Double.MAX_VALUE : tarifa.getRangoFinal();
			BigDecimal rangoInicial = new BigDecimal(String.valueOf(tarifa.getRangoInicial()));
			BigDecimal rangoFinal = new BigDecimal(String.valueOf(doubleRangoFinal));
			BigDecimal distancia = new BigDecimal(String.valueOf(doubleDistancia));
			if (rangoInicial.compareTo(distancia) < 0 && rangoFinal.compareTo(distancia) >= 0) {
				return tarifa.getTarifa();
			}
		}
		return 0D;
	}
}
