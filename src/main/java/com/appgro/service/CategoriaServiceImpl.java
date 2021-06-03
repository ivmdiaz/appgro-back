package com.appgro.service;

import com.appgro.exception.NegocioException;
import com.appgro.model.entity.GenCategoriaProducto;
import com.appgro.model.entity.GenEtiqueta;
import com.appgro.model.request.CategoriaRequest;
import com.appgro.model.response.CategoriaResponse;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.repository.GenCategoriaProductoRepository;
import com.appgro.repository.GenProductoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	private final GenCategoriaProductoRepository mGenCategoriaProductoRepository;
	private final GenProductoRepository mGenProductoRepository;

	public CategoriaServiceImpl(GenCategoriaProductoRepository mGenCategoriaProductoRepository,
			GenProductoRepository mGenProductoRepository) {
		this.mGenCategoriaProductoRepository = mGenCategoriaProductoRepository;
		this.mGenProductoRepository = mGenProductoRepository;
	}

	/**
	 * @see CategoriaService#listarCategorias()
	 */
	@Override
	public EntidadResponse<List<CategoriaResponse>> listarCategorias() {
		EntidadResponse<List<CategoriaResponse>> respuesta = new EntidadResponse<>();
		try {
			List<CategoriaResponse> categorias = mGenCategoriaProductoRepository.findAllResponse();
			respuesta.setEntidad(categorias);
			respuesta.setEstado(R0024);

		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}

	@Override
	public EstadoResponse guardarCategoria(CategoriaRequest request) {
		try {

			GenCategoriaProducto mCategoria = new GenCategoriaProducto();

			if (Objects.nonNull(request.getIdCategoria())) {
				mCategoria = mGenCategoriaProductoRepository.findById(request.getIdCategoria()).orElse(null);
			}

			if (Objects.nonNull(mCategoria)) {
				mCategoria.setCategoria(request.getCategoria().trim());
				mCategoria.setImagen(request.getImagen());
				mGenCategoriaProductoRepository.save(mCategoria);
				return R0061;
			}

			return R0062;

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}

	@Override
	public EstadoResponse eliminarCategoria(Integer idCategoria) {
		try {

			Optional<GenCategoriaProducto> mEtiqueta = mGenCategoriaProductoRepository.findById(idCategoria);

			if (Objects.nonNull(mEtiqueta.get()) && !mGenProductoRepository.existByIdCategoria(idCategoria)) {
				mGenCategoriaProductoRepository.delete(mEtiqueta.get());
				return R0057;
			} else {
				return R0058;
			}

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}
}
