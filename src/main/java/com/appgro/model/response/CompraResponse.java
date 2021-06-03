package com.appgro.model.response;

import java.io.Serializable;
import java.util.Date;

import com.appgro.model.entity.AppCarritoCompra;
import com.appgro.model.entity.GenProducto;
import com.appgro.model.entity.GenTienda;
import com.appgro.model.entity.GenUsuario;
import com.appgro.model.entity.GenUsuarioUbicacion;

public class CompraResponse implements Serializable {

	private static final long serialVersionUID = 134262316782930356L;

	private Long idCompra;
	private Date fecha;

	private Integer idTienda;
	private String nombreTienda;

	private Long idComprador;
	private String nombreComprador;
	private String celularComprador;
	private String correoComprador;

	private Integer idProducto;
	private String nombreProducto;
	private Integer cantidad;
	private String unidadProducto;

	private Double flete;
	private Double precioTotal;
	private Double total;

	private String aclaracion;
	private String estado;

	private String barrio;
	private String direccion;
	private String ciudad;
	private String direccionDatosAdicionales;
	private Double direccionLatitud;
	private Double direccionLongitud;

	private Date fechaEntrega;

	public CompraResponse(AppCarritoCompra c, GenProducto p, GenUsuario u, GenTienda t, GenUsuarioUbicacion ub) {
		this.setIdCompra(c.getIdCarritoCompra());
		this.setFecha(c.getRegDate());

		this.setIdTienda(t.getIdTienda());
		this.setNombreTienda(t.getNombre());

		this.setIdComprador(u.getIdUsuario());
		this.setNombreComprador(String.format("%s %s", u.getNombreCompleto(), u.getPrimerApellido()));
		this.setCelularComprador(u.getCelular());
		this.setCorreoComprador(u.getCorreo());

		this.setIdProducto(p.getIdProducto());
		this.setNombreProducto(p.getNombre());
		this.setCantidad(c.getCantidad());
		this.setUnidadProducto(p.getUnidadMedida());

		this.setFlete(c.getFlete());
		this.setPrecioTotal(c.getPrecio() * c.getCantidad());
		this.setTotal(this.getPrecioTotal() + this.getFlete());

		this.setAclaracion(c.getAclaracion());
		this.setEstado(c.getEstado());
		this.setFechaEntrega(c.getFechaEntrega());

		this.setBarrio(ub.getBarrio());
		this.setDireccion(this.obtenerDireccion(ub));
		this.setCiudad(ub.getCiudad().getNombre());
		this.setDireccionDatosAdicionales(ub.getDatosAdicionales());
		this.setDireccionLatitud(ub.getLatitud());
		this.setDireccionLongitud(ub.getLongitud());

	}

	private String obtenerDireccion(GenUsuarioUbicacion ub) {
		return String.format("%s # %s - %s", ub.getVia(), ub.getNumeroVia(), ub.getCuadrante());
	}

	public Long getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Long idCompra) {
		this.idCompra = idCompra;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public Long getIdComprador() {
		return idComprador;
	}

	public void setIdComprador(Long idComprador) {
		this.idComprador = idComprador;
	}

	public String getNombreComprador() {
		return nombreComprador;
	}

	public void setNombreComprador(String nombreComprador) {
		this.nombreComprador = nombreComprador;
	}

	public String getCelularComprador() {
		return celularComprador;
	}

	public void setCelularComprador(String celularComprador) {
		this.celularComprador = celularComprador;
	}

	public String getCorreoComprador() {
		return correoComprador;
	}

	public void setCorreoComprador(String correoComprador) {
		this.correoComprador = correoComprador;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getUnidadProducto() {
		return unidadProducto;
	}

	public void setUnidadProducto(String unidadProducto) {
		this.unidadProducto = unidadProducto;
	}

	public Double getFlete() {
		if(flete == null) {
			flete=0D;
		}
		return flete;
	}

	public void setFlete(Double flete) {
		this.flete = flete;
	}

	public Double getPrecioTotal() {
		if(precioTotal == null) {
			precioTotal=0D;
		}
		return precioTotal;
	}

	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
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

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccionDatosAdicionales() {
		return direccionDatosAdicionales;
	}

	public void setDireccionDatosAdicionales(String direccionDatosAdicionales) {
		this.direccionDatosAdicionales = direccionDatosAdicionales;
	}

	public Double getDireccionLatitud() {
		return direccionLatitud;
	}

	public void setDireccionLatitud(Double direccionLatitud) {
		this.direccionLatitud = direccionLatitud;
	}

	public Double getDireccionLongitud() {
		return direccionLongitud;
	}

	public void setDireccionLongitud(Double direccionLongitud) {
		this.direccionLongitud = direccionLongitud;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

}
