package com.appgro.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gen_ciudad")
public class GenCiudad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CIUDAD")
    private Integer idCiudad;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CODIGO")
    private String codigo;

    @ManyToOne()
    @JoinColumn(name = "ID_DEPARTAMENTO")
    private GenDepartamento idDepartamento;


    public GenCiudad() {
    }

    public GenCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public GenCiudad(Integer idCiudad, String nombre) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public GenDepartamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(GenDepartamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCiudad != null ? idCiudad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GenCiudad)) {
            return false;
        }
        GenCiudad other = (GenCiudad) object;
        if ((this.idCiudad == null && other.idCiudad != null) || (this.idCiudad != null && !this.idCiudad.equals(other.idCiudad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GenCiudad[ idCiudad=" + idCiudad + " ]";
    }
}
