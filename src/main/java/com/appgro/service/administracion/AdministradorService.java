package com.appgro.service.administracion;

import java.util.List;

import com.appgro.model.request.ActualizarPerfilUsuarioRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.model.response.UsuarioResponse;

public interface AdministradorService {
	
	EntidadResponse<List<UsuarioResponse>> obtenerUsuarios();
	
	EstadoResponse actualizarPerfilUsuario(ActualizarPerfilUsuarioRequest request);
	
	EstadoResponse inhabilitarUsuario(Long idUsuario);
	
	EstadoResponse habilitarUsuario(Long idUsuario);
	
	EntidadResponse<List<IdNombreResponse>> obtenerTiendasSelect();
}
