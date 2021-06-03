package com.appgro.service.administracion;

import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.appgro.exception.NegocioException;
import com.appgro.model.entity.GenPerfil;
import com.appgro.model.entity.GenUsuario;
import com.appgro.model.request.ActualizarPerfilUsuarioRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.model.response.UsuarioResponse;
import com.appgro.repository.GenTiendaRepository;
import com.appgro.repository.GenUsuarioRepository;
import com.appgro.service.PerfilService;

@Service
public class AdministradorServiceImpl implements AdministradorService {

	private final GenUsuarioRepository mUsuarioRepository;
	private final PerfilService mPerfilService;
	private final GenTiendaRepository mGenTiendaRepository;

	public AdministradorServiceImpl(GenUsuarioRepository mUsuarioRepository, PerfilService mPerfilService,
			GenTiendaRepository mGenTiendaRepository) {
		this.mUsuarioRepository = mUsuarioRepository;
		this.mPerfilService = mPerfilService;
		this.mGenTiendaRepository = mGenTiendaRepository;
	}

	@Override
	public EntidadResponse<List<UsuarioResponse>> obtenerUsuarios() {
		final EntidadResponse<List<UsuarioResponse>> respuesta = new EntidadResponse<>();
		try {
			respuesta.setEntidad(mUsuarioRepository.findByResponse());
			respuesta.setEstado(R0063);
		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}

	@Override
	public EstadoResponse actualizarPerfilUsuario(ActualizarPerfilUsuarioRequest request) {
		try {
			if (!mUsuarioRepository.existUsuarioPerfilAdministrador(request.getIdUsuario())
					&& GenPerfil.ID_PERFIL_ADMINISTRADOR != request.getIdPerfil()) {
				return R0064;
			}

			return mPerfilService.asignarPerfilUsuario(request.getIdUsuario(), request.getIdPerfil());

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}

	@Override
	public EstadoResponse inhabilitarUsuario(Long idUsuario) {
		try {

			GenUsuario mUsuario = this.mUsuarioRepository.findFirstByIdUsuario(idUsuario);

			if (Objects.nonNull(mUsuario)) {
				mUsuario.setEstado(GenUsuario.ESTADO_INHABILITADO);
				this.mUsuarioRepository.save(mUsuario);
				return R0065;
			}
			return R0066;

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}

	@Override
	public EstadoResponse habilitarUsuario(Long idUsuario) {
		try {

			GenUsuario mUsuario = this.mUsuarioRepository.findFirstByIdUsuario(idUsuario);

			if (Objects.nonNull(mUsuario)) {
				mUsuario.setEstado(GenUsuario.ESTADO_HABILITADO);
				this.mUsuarioRepository.save(mUsuario);
				return R0070;
			}
			return R0071;

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}

	@Override
	public EntidadResponse<List<IdNombreResponse>> obtenerTiendasSelect() {
		EntidadResponse<List<IdNombreResponse>> respuesta = new EntidadResponse<>();
		try {
			// Obtenemos las tiendas segun los parámetros.
			List<IdNombreResponse> tiendas = mGenTiendaRepository.findAllSelect();
			respuesta.setEntidad(tiendas);
			respuesta.setEstado(R0016);
		} catch (NegocioException ex) {
			respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			respuesta.setEstado(exceptiontoEstadoResponse(ex));
		}
		return respuesta;
	}

}
