package com.appgro.model.response;

public class ReporteProductoResponse {

	// Datos de la tienda
	private String nombreTienda;
	private String nombreProducto;
	private String categoriaProducto;
	private Double precioProducto;
	private Integer disponibilidadProducto;
	private String unidadMedidaProducto;

	// Datos de agrupación
	private Double totalVentas;
	private Long totalUnidades;

	public ReporteProductoResponse(String nombreTienda, String nombreProducto, String categoriaProducto,
			Double precioProducto, Integer disponibilidadProducto, String unidadMedidaProducto, Double totalVentas,
			Long totalUnidades) {
		super();
		this.nombreTienda = nombreTienda;
		this.nombreProducto = nombreProducto;
		this.categoriaProducto = categoriaProducto;
		this.precioProducto = precioProducto;
		this.disponibilidadProducto = disponibilidadProducto;
		this.unidadMedidaProducto = unidadMedidaProducto;
		this.totalVentas = totalVentas;
		this.totalUnidades = totalUnidades;
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getCategoriaProducto() {
		return categoriaProducto;
	}

	public void setCategoriaProducto(String categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}

	public Double getPrecioProducto() {
		return precioProducto;
	}

	public void setPrecioProducto(Double precioProducto) {
		this.precioProducto = precioProducto;
	}

	public Integer getDisponibilidadProducto() {
		return disponibilidadProducto;
	}

	public void setDisponibilidadProducto(Integer disponibilidadProducto) {
		this.disponibilidadProducto = disponibilidadProducto;
	}

	public String getUnidadMedidaProducto() {
		return unidadMedidaProducto;
	}

	public void setUnidadMedidaProducto(String unidadMedidaProducto) {
		this.unidadMedidaProducto = unidadMedidaProducto;
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

}
