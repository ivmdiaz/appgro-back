package com.appgro.service;

import com.appgro.exception.NegocioException;
import com.appgro.model.entity.GenUsuarioTarifa;
import com.appgro.model.request.IdentificadorRequest;
import com.appgro.model.response.*;
import com.appgro.repository.AppCarritoCompraRepository;
import com.appgro.repository.GenProductoRepository;
import com.appgro.repository.GenTiendaRepository;
import com.appgro.repository.GenUsuarioRepository;
import com.appgro.repository.GenUsuarioTarifasRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

@Service
public class ProveedorServiceImpl implements ProveedorService {

	private final GenUsuarioRepository mUsuarioRepository;
	private final AppCarritoCompraRepository mAppCarritoCompraRepository;
	private final GenTiendaRepository mGenTiendaRepository;
	private final GenUsuarioTarifasRepository mGenUsuarioTarifasRepository;
	private final GenProductoRepository mGenProductoRepository;


	public ProveedorServiceImpl(GenUsuarioRepository mUsuarioRepository,
			AppCarritoCompraRepository mAppCarritoCompraRepository, GenTiendaRepository mGenTiendaRepository,
			GenUsuarioTarifasRepository mGenUsuarioTarifasRepository, 
			final GenProductoRepository mGenProductoRepository) {
		this.mUsuarioRepository = mUsuarioRepository;
		this.mAppCarritoCompraRepository = mAppCarritoCompraRepository;
		this.mGenTiendaRepository = mGenTiendaRepository;
		this.mGenUsuarioTarifasRepository = mGenUsuarioTarifasRepository;
		this.mGenProductoRepository = mGenProductoRepository;
	}

	@Override
	public EntidadResponse<List<CompraResponse>> obtenerCompras() {
		EntidadResponse<List<CompraResponse>> respuesta = new EntidadResponse<>();
		try {
			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());
			List<CompraResponse> productos = mAppCarritoCompraRepository
					.consultarComprasByProveedorOrderByRegDateDesc(idUsuario);
			respuesta.setEntidad(productos);
			respuesta.setEstado(R0051);
		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}

	@Override
	public EntidadResponse<List<TiendaResponse>> obtenerTiendas() {
		EntidadResponse<List<TiendaResponse>> respuesta = new EntidadResponse<>();
		try {
			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

			// Obtenemos las tiendas segun los parámetros.
			List<TiendaResponse> tiendas = mGenTiendaRepository.findAllByProveedor(idUsuario);
			respuesta.setEntidad(tiendas);
			respuesta.setEstado(R0016);
		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}

	@Override
	public EntidadResponse<List<TarifasResponse>> obtenerTarifas() {
		EntidadResponse<List<TarifasResponse>> respuesta = new EntidadResponse<>();
		try {
			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

			// Obtenemos las tiendas segun los parámetros.
			List<GenUsuarioTarifa> tiendas = mGenUsuarioTarifasRepository
					.findByIdUsuarioOrderByRangoInicial(idUsuario);
			List<TarifasResponse> lstResponse = tiendas.stream().map(bd -> new TarifasResponse(bd))
					.collect(Collectors.toList());
			respuesta.setEntidad(lstResponse);
			respuesta.setEstado(R0054);
		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}
	
	@Override
	@Transactional(rollbackOn = { RuntimeException.class })
	public EstadoResponse guardarTarifas(List<TarifasResponse> tarifas) {
		try {
			
			this.validarTarifas(tarifas);

            //Obtenemos el id usuario del JWT @NotNull
            Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

            //Mapeamos el objeto Peticion a la entidad.
			List<GenUsuarioTarifa> tarifasbd = tarifas.stream().map(tar -> new GenUsuarioTarifa(tar, idUsuario))
					.collect(Collectors.toList());
            
            //Guardamos el registro en base de datos:
			mGenUsuarioTarifasRepository.saveAll(tarifasbd);

            return R0069;

        } catch (NegocioException ex) {
            return negocioExceptiontoEstadoResponse(ex);
        } catch (Exception ex) {
            return exceptiontoEstadoResponse(ex);
        }
	}

	@Override
	@Transactional(rollbackOn = { RuntimeException.class })
	public EstadoResponse entregarPedido(IdentificadorRequest request) {
		try {

			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

			if (mAppCarritoCompraRepository.existByIdProveedor(request.getId(), idUsuario)) {
				mAppCarritoCompraRepository.actualizarEstadoAEntregado(request.getId());
				return R0055;
			} else {
				return R0056;
			}

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}
	
	@Override
	@Transactional(rollbackOn = { RuntimeException.class })
	public EntidadResponse<List<IdNombreResponse>> obtenerTiendasSelect() {
		EntidadResponse<List<IdNombreResponse>> respuesta = new EntidadResponse<>();
		try {
			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

			// Obtenemos las tiendas segun los parámetros.
			List<IdNombreResponse> tiendas = mGenTiendaRepository.findAllByProveedorSelect(idUsuario);
			respuesta.setEntidad(tiendas);
			respuesta.setEstado(R0016);
		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}

	@Override
	@Transactional(rollbackOn = { RuntimeException.class })
	public EntidadResponse<List<IdNombreResponse>> obtenerProductosSelect() {
		EntidadResponse<List<IdNombreResponse>> respuesta = new EntidadResponse<>();
		try {
			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

			// Obtenemos las tiendas segun los parámetros.
			List<IdNombreResponse> tiendas = mGenProductoRepository.findAllByIdVendedorSelect(idUsuario);
			respuesta.setEntidad(tiendas);
			respuesta.setEstado(R0016);
		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}

	@Override
	@Transactional(rollbackOn = { RuntimeException.class })
	public EntidadResponse<List<IdNombreResponse>> obtenerCategoriasSelect() {
		EntidadResponse<List<IdNombreResponse>> respuesta = new EntidadResponse<>();
		try {
			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

			// Obtenemos las tiendas segun los parámetros.
			List<IdNombreResponse> tiendas = mGenProductoRepository.findAllCategoriasByIdVendedorSelect(idUsuario);
			respuesta.setEntidad(tiendas);
			respuesta.setEstado(R0016);
		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}
	
	private void validarTarifas(List<TarifasResponse> tarifas) throws NegocioException {

		if (tarifas != null && tarifas.size() > 1) {

			// Ordenar tarifas segun su rango inicial.
			Collections.sort(tarifas, (p1, p2) -> p1.getRangoInicial().compareTo(p2.getRangoInicial()));

			for (int i = 1; i < tarifas.size(); i++) {

				// Tarifa anterior:
				TarifasResponse tarifaAnterior = tarifas.get(i - 1);

				// Tarifa actual:
				TarifasResponse tarifaActual = tarifas.get(i);

				// Validación 1: Que el rango final de la tarifa anterior se igual a la tarifa
				// inicial de la tarifa actual.
				if (Double.compare(tarifaAnterior.getRangoFinal(), tarifaActual.getRangoInicial()) != 0) {
					NegocioException ex = new NegocioException("El rango final debe ser igual al rango inicial de la siguiente tarifa.");
					ex.addError("El rango final debe ser igual al rango inicial de la siguiente tarifa.");
					throw ex;
				}
			}

		}
	}

}
