package com.appgro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appgro.model.request.ActualizarPerfilUsuarioRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.model.response.UsuarioResponse;
import com.appgro.service.administracion.AdministradorService;

@RestController
@RequestMapping("/api/administrador")
public class UsuarioAdministradorController {
	
	private final AdministradorService mAdministradorService; 
	
	public UsuarioAdministradorController(AdministradorService mAdministradorService) {
		this.mAdministradorService = mAdministradorService;
	}
	
	@GetMapping("/obtenerUsuarios")
    public EntidadResponse<List<UsuarioResponse>> obtenerUsuarios() {
        return mAdministradorService.obtenerUsuarios();
    }
	
	@PostMapping("/actualizarPerfil")
    public EstadoResponse actualizarPerfil(@RequestBody ActualizarPerfilUsuarioRequest request) {
        return mAdministradorService.actualizarPerfilUsuario(request);
    }
	
	@DeleteMapping("/inhabilitarUsuario/{idUsuario}")
    public EstadoResponse inhabilitarUsuario(@PathVariable Long idUsuario) {
        return mAdministradorService.inhabilitarUsuario(idUsuario);
    }
	
	@PutMapping("/habilitarUsuario/{idUsuario}")
    public EstadoResponse habilitarUsuario(@PathVariable Long idUsuario) {
        return mAdministradorService.habilitarUsuario(idUsuario);
    }
	
	@GetMapping("/obtenerTiendasSelect")
    public EntidadResponse<List<IdNombreResponse>> obtenerTiendasSelect() {
        return mAdministradorService.obtenerTiendasSelect();
    }
	
}
