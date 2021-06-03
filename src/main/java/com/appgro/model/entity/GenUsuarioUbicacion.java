package com.appgro.model.entity;

import com.appgro.model.request.RegistroDomicilioRequest;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gen_usuario_ubicacion")
public class GenUsuarioUbicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_UBICACION")
    private Long idUbicacion;

    @Column(name = "BARRIO")
    private String barrio;

    @Column(name = "VIA")
    private String via;

    @Column(name = "NUMERO_VIA")
    private String numeroVia;

    @Column(name = "CUADRANTE")
    private String cuadrante;

    @Column(name = "DATOS_ADICIONALES")
    private String datosAdicionales;

    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @JoinColumn(name = "ID_CIUDAD")
    @ManyToOne()
    private GenCiudad ciudad;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "PREDETERMINADO")
    private Boolean predeterminado;

    @Column(name = "LONGITUD")
    private Double longitud;

    @Column(name = "LATITUD")
    private Double latitud;

    public GenUsuarioUbicacion() {
    }

    public GenUsuarioUbicacion(RegistroDomicilioRequest peticion) {
        this.setIdUbicacion(peticion.getIdUbicacion());
        this.setBarrio(peticion.getBarrio());
        this.setVia(peticion.getVia());
        this.setNumeroVia(peticion.getNumeroVia());
        this.setCuadrante(peticion.getCuadrante());
        this.setDatosAdicionales(peticion.getDatosAdicionales());
        this.setTelefono(peticion.getTelefono());
        this.setCiudad(new GenCiudad(peticion.getIdCiudad()));
        this.setLongitud(peticion.getLongitud());
        this.setLatitud(peticion.getLatitud());
        this.setPredeterminado(Boolean.FALSE);
    }

    public GenUsuarioUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public GenUsuarioUbicacion(Long idUbicacion, String via, String numeroVia, String cuadrante, String telefono) {
        this.idUbicacion = idUbicacion;
        this.via = via;
        this.numeroVia = numeroVia;
        this.cuadrante = cuadrante;
        this.telefono = telefono;
    }

    public Long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumeroVia() {
        return numeroVia;
    }

    public void setNumeroVia(String numeroVia) {
        this.numeroVia = numeroVia;
    }

    public String getCuadrante() {
        return cuadrante;
    }

    public void setCuadrante(String cuadrante) {
        this.cuadrante = cuadrante;
    }

    public String getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(String datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public Boolean getPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(Boolean predeterminado) {
        this.predeterminado = predeterminado;
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
        hash += (idUbicacion != null ? idUbicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GenUsuarioUbicacion)) {
            return false;
        }
        GenUsuarioUbicacion other = (GenUsuarioUbicacion) object;
        if ((this.idUbicacion == null && other.idUbicacion != null)
                || (this.idUbicacion != null && !this.idUbicacion.equals(other.idUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GenUsuarioUbicacion[ idUbicacion=" + idUbicacion + " ]";
    }
}
