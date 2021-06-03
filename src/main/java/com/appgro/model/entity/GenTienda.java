package com.appgro.model.entity;

import com.appgro.model.request.RegistroTiendaRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "gen_tienda")
public class GenTienda implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ESTADO_HABILITADA = "HABILITADA";
    public static final String ESTADO_ELIMINADA = "ELIMINADA";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TIENDA")
    private Integer idTienda;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "TELEFONO1")
    private String telefono1;

    @Column(name = "TELEFONO2")
    private String telefono2;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "REG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @JoinColumn(name = "ID_CIUDAD")
    @ManyToOne()
    private GenCiudad ciudad;

    @Column(name = "IMAGEN")
    @Basic(fetch = FetchType.LAZY)
    private String imagen;

    @Column(name = "LONGITUD")
    private Double longitud;

    @Column(name = "LATITUD")
    private Double latitud;


    public GenTienda() {
    }

    public GenTienda(RegistroTiendaRequest peticion) {
    	this.setIdTienda(peticion.getIdTienda());
        this.setNombre(peticion.getNombre());
        this.setDescripcion(peticion.getDescripcion());
        this.setDireccion(peticion.getDireccion());
        this.setTelefono1(peticion.getTelefono1());
        this.setTelefono2(peticion.getTelefono2());
        this.setCorreo(peticion.getCorreo());
        this.setCiudad(new GenCiudad(peticion.getIdCiudad()));
        this.setLatitud(peticion.getLatitud());
        this.setLongitud(peticion.getLongitud());
        this.setImagen(peticion.getImagen());
    }

    public GenTienda(Integer idTienda) {
        this.idTienda = idTienda;
    }

    public GenTienda(Integer idTienda, Date regDate) {
        this.idTienda = idTienda;
        this.regDate = regDate;
    }

    public Integer getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(Integer idTienda) {
        this.idTienda = idTienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public GenCiudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(GenCiudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTienda != null ? idTienda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GenTienda)) {
            return false;
        }
        GenTienda other = (GenTienda) object;
        if ((this.idTienda == null && other.idTienda != null) ||
                (this.idTienda != null && !this.idTienda.equals(other.idTienda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.GenTienda[ idTienda=" + idTienda + " ]";
    }

}
