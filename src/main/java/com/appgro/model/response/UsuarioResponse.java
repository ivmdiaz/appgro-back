package com.appgro.model.response;

import com.appgro.model.entity.GenPerfil;
import com.appgro.model.entity.GenUsuario;
import com.appgro.model.entity.GenUsuarioUbicacion;

import java.io.Serializable;
import java.util.List;

public class UsuarioResponse implements Serializable {

	private Long idUsuario;
	private String nombreCompleto;
	private String primerApellido;
	private String segundoApellido;
	private String correo;
	private String celular;
	private String estado;
	private List<GenPerfil> perfiles;
	private GenUsuarioUbicacion domicilioPredeterminado;

	public UsuarioResponse(GenUsuario usuario) {
		this.setIdUsuario(usuario.getIdUsuario());
		this.setNombreCompleto(usuario.getNombreCompleto());
		this.setPrimerApellido(usuario.getPrimerApellido());
		this.setSegundoApellido(usuario.getSegundoApellido());
		this.setCorreo(usuario.getCorreo());
		this.setCelular(usuario.getCelular());
		this.setPerfiles(usuario.getPerfiles());
		this.setEstado(usuario.getEstado());
	}

	public UsuarioResponse(GenUsuario usuario, GenUsuarioUbicacion ubicacion) {
		this.setIdUsuario(usuario.getIdUsuario());
		this.setNombreCompleto(usuario.getNombreCompleto());
		this.setPrimerApellido(usuario.getPrimerApellido());
		this.setSegundoApellido(usuario.getSegundoApellido());
		this.setCorreo(usuario.getCorreo());
		this.setCelular(usuario.getCelular());
		this.setPerfiles(usuario.getPerfiles());
		this.setDomicilioPredeterminado(ubicacion);
		this.setEstado(usuario.getEstado());
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public List<GenPerfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<GenPerfil> perfiles) {
		this.perfiles = perfiles;
	}

	public GenUsuarioUbicacion getDomicilioPredeterminado() {
		return domicilioPredeterminado;
	}

	public void setDomicilioPredeterminado(GenUsuarioUbicacion domicilioPredeterminado) {
		this.domicilioPredeterminado = domicilioPredeterminado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
