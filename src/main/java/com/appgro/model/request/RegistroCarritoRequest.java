package com.appgro.model.request;

public class RegistroCarritoRequest {

	private Long idCarritoCompra;
	private Integer idProducto;
	private Integer cantidad;
	private String aclaracion;
	private Long idUbicacion;
	private Double precioUnitario;

	public Long getIdCarritoCompra() {
		return idCarritoCompra;
	}

	public void setIdCarritoCompra(Long idCarritoCompra) {
		this.idCarritoCompra = idCarritoCompra;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getAclaracion() {
		return aclaracion;
	}

	public void setAclaracion(String aclaracion) {
		this.aclaracion = aclaracion;
	}

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

}
