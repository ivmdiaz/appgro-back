package com.appgro.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gen_parametro")
public class GenParametro implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer PAR_ENDPOINT_RECUPERAR_CONTRASENA = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PARAMETRO")
    private Integer idParametro;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "VALOR")
    private String valor;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "TIPO_PARAMETRO")
    private String tipoParametro;

    public GenParametro() {
    }

    public GenParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoParametro() {
        return tipoParametro;
    }

    public void setTipoParametro(String tipoParametro) {
        this.tipoParametro = tipoParametro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GenParametro)) {
            return false;
        }
        GenParametro other = (GenParametro) object;
        if ((this.idParametro == null && other.idParametro != null)
                || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GenParametro[ idParametro=" + idParametro + " ]";
    }
}
