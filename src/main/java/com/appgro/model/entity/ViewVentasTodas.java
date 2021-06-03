package com.appgro.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.appgro.util.EncriptadorAES;

@Entity
@Table(name = "v_carrito_tienda_producto")
public class ViewVentasTodas {

	@Transient
	public final EncriptadorAES AES = new EncriptadorAES();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_tienda")
	private Integer idTienda;

	@Column(name = "tienda")
	private String tienda;

	@Column(name = "id_producto")
	private Integer idProducto;

	@Column(name = "producto")
	private String producto;

	@Column(name = "id_categoria_producto")
	private Integer idCategoriaProducto;

	@Column(name = "categoria_producto")
	private String categoriaProducto;

	@Column(name = "nombre_comprador")
	private String nombreComprador;

	@Column(name = "cantidad_venta")
	private Integer cantidadVenta;

	@Column(name = "total_venta")
	private Double totalVenta;

	@Column(name = "id_departamento_venta")
	private Integer idDepartamentoVenta;

	@Column(name = "departamento_venta")
	private String departamentoVenta;

	@Column(name = "id_ciudad_venta")
	private Integer idCiudadVenta;

	@Column(name = "ciudad_venta")
	private String ciudadVenta;

	@Column(name = "fecha_venta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaVenta;

	@Column(name = "fecha_entrega")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEntrega;

	@Column(name = "id_vendedor")
	private Long idVendedor;

	@Column(name = "estado_venta")
	private String estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIdTienda() {
		return idTienda;
	}

	public void setIdTienda(Integer idTienda) {
		this.idTienda = idTienda;
	}

	public String getTienda() {
		return tienda;
	}

	public void setTienda(String tienda) {
		this.tienda = tienda;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public Integer getIdCategoriaProducto() {
		return idCategoriaProducto;
	}

	public void setIdCategoriaProducto(Integer idCategoriaProducto) {
		this.idCategoriaProducto = idCategoriaProducto;
	}

	public String getCategoriaProducto() {
		return categoriaProducto;
	}

	public void setCategoriaProducto(String categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}

	public String getNombreComprador() {
		return AES.desencriptar(nombreComprador);
	}

	public void setNombreComprador(String nombreComprador) {
		this.nombreComprador = nombreComprador;
	}

	public Integer getCantidadVenta() {
		return cantidadVenta;
	}

	public void setCantidadVenta(Integer cantidadVenta) {
		this.cantidadVenta = cantidadVenta;
	}

	public Double getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(Double totalVenta) {
		this.totalVenta = totalVenta;
	}

	public Integer getIdDepartamentoVenta() {
		return idDepartamentoVenta;
	}

	public void setIdDepartamentoVenta(Integer idDepartamentoVenta) {
		this.idDepartamentoVenta = idDepartamentoVenta;
	}

	public String getDepartamentoVenta() {
		return departamentoVenta;
	}

	public void setDepartamentoVenta(String departamentoVenta) {
		this.departamentoVenta = departamentoVenta;
	}

	public Integer getIdCiudadVenta() {
		return idCiudadVenta;
	}

	public void setIdCiudadVenta(Integer idCiudadVenta) {
		this.idCiudadVenta = idCiudadVenta;
	}

	public String getCiudadVenta() {
		return ciudadVenta;
	}

	public void setCiudadVenta(String ciudadVenta) {
		this.ciudadVenta = ciudadVenta;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Long getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Long idVendedor) {
		this.idVendedor = idVendedor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
