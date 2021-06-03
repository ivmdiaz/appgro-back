package com.appgro.service;

import com.appgro.exception.NegocioException;
import com.appgro.model.entity.GenProducto;
import com.appgro.model.request.EliminarProductoRequest;
import com.appgro.model.request.RegistroProductoRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.ProductoResponse;
import com.appgro.repository.AppCarritoCompraRepository;
import com.appgro.repository.GenProductoRepository;
import com.appgro.repository.GenTiendaRepository;
import com.appgro.repository.GenUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

@Service
public class ProductoServiceImpl implements ProductoService {

	private final GenUsuarioRepository mUsuarioRepository;
	private final GenProductoRepository mGenProductoRepository;
	private final GenTiendaRepository mGenTiendaRepository;
	private final AppCarritoCompraRepository mAppCarritoCompraRepository;

	public ProductoServiceImpl(GenUsuarioRepository mUsuarioRepository, GenProductoRepository mGenProductoRepository,
			GenTiendaRepository mGenTiendaRepository, AppCarritoCompraRepository mAppCarritoCompraRepository) {
		this.mUsuarioRepository = mUsuarioRepository;
		this.mGenProductoRepository = mGenProductoRepository;
		this.mGenTiendaRepository = mGenTiendaRepository;
		this.mAppCarritoCompraRepository = mAppCarritoCompraRepository;
	}

	/**
	 * @see ProductoService#guardarProducto(RegistroProductoRequest)
	 */
	@Override
	public EstadoResponse guardarProducto(RegistroProductoRequest peticion) {
		try {

			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

			// Si la tienda está relacionada al usuario del token.
			if (idUsuario != null
					&& mGenTiendaRepository.existByIdTiendaAndIdUsuario(peticion.getIdTienda(), idUsuario)) {
				GenProducto mProducto = new GenProducto(peticion);
				mGenProductoRepository.save(mProducto);
				return R0017;
			} else {
				return R0018;
			}
		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}

	/**
	 * @see ProductoService#eliminarProducto(EliminarProductoRequest)
	 */
	@Override
	public EstadoResponse eliminarProducto(EliminarProductoRequest peticion) {
		try {
			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

			// Si la tienda está relacionada al usuario del token.
			if (this.isCanBeDelete(peticion, idUsuario)) {
				mGenProductoRepository.delete(new GenProducto(peticion));
				return R0019;
			} else {
				return R0020;
			}
		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}

	/**
	 * @see ProductoService#obtenerProductosByTienda(Integer)
	 */
	@Override
	public EntidadResponse<List<ProductoResponse>> obtenerProductosByTienda(Integer idTienda) {
		EntidadResponse<List<ProductoResponse>> respuesta = new EntidadResponse<>();
		try {
			List<ProductoResponse> productos = mGenProductoRepository.findAllByIdTienda(idTienda);
			respuesta.setEntidad(productos);
			respuesta.setEstado(R0021);
		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}
	
	private boolean isCanBeDelete(EliminarProductoRequest peticion, Long idUsuario) {
		return idUsuario != null && mGenTiendaRepository.existByIdTiendaAndIdUsuario(peticion.getIdTienda(), idUsuario)
				&& !mAppCarritoCompraRepository.existByIdProducto(peticion.getIdProducto());
	}

}
