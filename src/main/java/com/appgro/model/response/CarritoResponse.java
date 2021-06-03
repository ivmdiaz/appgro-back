package com.appgro.model.response;

import com.appgro.model.entity.AppCarritoCompra;
import com.appgro.model.entity.GenProducto;
import com.appgro.model.entity.GenUsuarioUbicacion;

import java.io.Serializable;
import java.util.Date;

public class CarritoResponse implements Serializable {

    private Long idCarritoCompra;
    private Integer idProducto;
    private Integer cantidad;
    private String aclaracion;
    private String estado;
    private Date fecha;
    private String nombreProducto;
    private Double precioUnitario;
    private Double costoFlete;
    private Double precioTotal;
    private String imagenProducto;
    private String direccion;
    private Date fechaEntrega;

    public CarritoResponse(AppCarritoCompra c, GenProducto p, GenUsuarioUbicacion ub) {
        this.setIdCarritoCompra(c.getIdCarritoCompra());
        this.setIdProducto(p.getIdProducto());
        this.setCantidad(c.getCantidad());
        this.setAclaracion(c.getAclaracion());
        this.setEstado(c.getEstado());
        this.setFecha(c.getRegDate());
        this.setNombreProducto(p.getNombre());
        this.setPrecioUnitario(p.getPrecio());
        this.setCostoFlete(c.getFlete());
        this.setPrecioTotal(p.getPrecio() * c.getCantidad() + this.getCostoFlete());
        this.setImagenProducto(p.getImagen());
        this.setFechaEntrega(c.getFechaEntrega());
        this.setDireccion(ub.getVia() + " # " + ub.getNumeroVia() + " - " + ub.getCuadrante());
    }

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

	public Double getCostoFlete() {
		if(costoFlete == null) {
			return 0D;
		}
		return costoFlete;
	}

	public void setCostoFlete(Double costoFlete) {
		this.costoFlete = costoFlete;
	}
    
    
}
