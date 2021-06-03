package com.appgro.model.entity;

import com.appgro.model.request.RegistroCarritoRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "app_carrito_compra")
public class AppCarritoCompra implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String ESTADO_EN_PROCESO = "PROCESO";
	public static final String ESTADO_COMPRADO = "COMPRADO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CARRITO_COMPRA")
	private Long idCarritoCompra;

	@Column(name = "PRECIO")
	private Double precio;

	@Column(name = "FLETE")
	private Double flete;
	
	@Column(name = "CANTIDAD")
	private Integer cantidad;

	@Column(name = "ACLARACION")
	private String aclaracion;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "REG_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;

	@Column(name = "FECHA_ENTREGA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEntrega;

	@Column(name = "ID_COMPRADOR")
	private Long idComprador;

	@Column(name = "ID_PRODUCTO")
	private Integer idProducto;

	@Column(name = "ID_UBICACION")
	private Long idUbicacion;

	public AppCarritoCompra() {
	}

	public AppCarritoCompra(Long idCarritoCompra) {
		this.idCarritoCompra = idCarritoCompra;
	}

	public AppCarritoCompra(Long idCarritoCompra, int cantidad, String estado, Date regDate) {
		this.idCarritoCompra = idCarritoCompra;
		this.cantidad = cantidad;
		this.estado = estado;
		this.regDate = regDate;
	}

	public AppCarritoCompra(RegistroCarritoRequest peticion, Long idUsuario) {
		this.setIdCarritoCompra(peticion.getIdCarritoCompra());
		this.setIdProducto(peticion.getIdProducto());
		this.setCantidad(peticion.getCantidad());
		this.setAclaracion(peticion.getAclaracion());
		this.setIdComprador(idUsuario);
		this.setIdUbicacion(peticion.getIdUbicacion());
		this.setPrecio(peticion.getPrecioUnitario());
		this.setEstado(ESTADO_EN_PROCESO);
	}

	public Long getIdCarritoCompra() {
		return idCarritoCompra;
	}

	public void setIdCarritoCompra(Long idCarritoCompra) {
		this.idCarritoCompra = idCarritoCompra;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
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

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Long getIdComprador() {
		return idComprador;
	}

	public void setIdComprador(Long idComprador) {
		this.idComprador = idComprador;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	public Double getFlete() {
		return flete;
	}

	public void setFlete(Double flete) {
		this.flete = flete;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idCarritoCompra != null ? idCarritoCompra.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AppCarritoCompra)) {
			return false;
		}
		AppCarritoCompra other = (AppCarritoCompra) object;
		if ((this.idCarritoCompra == null && other.idCarritoCompra != null)
				|| (this.idCarritoCompra != null && !this.idCarritoCompra.equals(other.idCarritoCompra))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AppCarritoCompra[ idCarritoCompra=" + idCarritoCompra + " ]";
	}
}
