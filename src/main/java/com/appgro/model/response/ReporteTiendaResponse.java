package com.appgro.model.response;

import com.appgro.model.entity.GenTienda;
import com.appgro.model.entity.GenUsuario;

public class ReporteTiendaResponse {

	// Datos de la tienda
	private Integer idTienda;
	private String nombreTienda;
	private String direccion;
	private String telefono1;
	private String correo;
	private String departamentoTienda;
	private String ciudadTienda;
	private String vendedorTienda;

	// Datos de agrupación
	private Double totalVentas;
	private Long totalUnidades;

	/**
	 * Datos de administradores
	 */
	public ReporteTiendaResponse(GenTienda tienda, GenUsuario usuario, Double totalVentas, Long totalUnidades) {
		this.idTienda = tienda.getIdTienda();
		this.nombreTienda = tienda.getNombre();
		this.direccion = tienda.getDireccion();
		this.telefono1 = tienda.getTelefono1();
		this.correo = tienda.getCorreo();
		this.vendedorTienda = usuario.getNombreCompleto() + " " + usuario.getPrimerApellido();

		if (tienda.getCiudad() != null) {
			this.ciudadTienda = tienda.getCiudad().getNombre();
			if (tienda.getCiudad().getIdDepartamento() != null) {
				this.departamentoTienda = tienda.getCiudad().getIdDepartamento().getNombre();
			}
		}

		this.totalVentas = totalVentas;
		this.totalUnidades = totalUnidades;
	}

	/**
	 * Datos de proveedores
	 */
	public ReporteTiendaResponse(String nombreTienda, String direccion, Double totalVentas, Long totalUnidades) {
		super();
		this.nombreTienda = nombreTienda;
		this.direccion = direccion;
		this.totalVentas = totalVentas;
		this.totalUnidades = totalUnidades;
	}

	public Integer getIdTienda() {
		return idTienda;
	}

	public void setIdTienda(Integer idTienda) {
		this.idTienda = idTienda;
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDepartamentoTienda() {
		return departamentoTienda;
	}

	public void setDepartamentoTienda(String departamentoTienda) {
		this.departamentoTienda = departamentoTienda;
	}

	public String getCiudadTienda() {
		return ciudadTienda;
	}

	public void setCiudadTienda(String ciudadTienda) {
		this.ciudadTienda = ciudadTienda;
	}

	public Double getTotalVentas() {
		return totalVentas;
	}

	public void setTotalVentas(Double totalVentas) {
		this.totalVentas = totalVentas;
	}

	public Long getTotalUnidades() {
		return totalUnidades;
	}

	public void setTotalUnidades(Long totalUnidades) {
		this.totalUnidades = totalUnidades;
	}

	public String getVendedorTienda() {
		return vendedorTienda;
	}

	public void setVendedorTienda(String vendedorTienda) {
		this.vendedorTienda = vendedorTienda;
	}

}
