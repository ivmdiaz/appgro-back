package com.appgro.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gen_departamento")
public class GenDepartamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ID_DEPARTAMENTO")
    private Integer idDepartamento;
    @Basic(optional = false)

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CODIGO")
    private String codigo;

    @ManyToOne()
    @JoinColumn(name = "ID_PAIS")
    private GenPais idPais;

    public GenDepartamento() {
    }

    public GenDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public GenDepartamento(Integer idDepartamento, String nombre) {
        this.idDepartamento = idDepartamento;
        this.nombre = nombre;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
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

    public GenPais getIdPais() {
        return idPais;
    }

    public void setIdPais(GenPais idPais) {
        this.idPais = idPais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDepartamento != null ? idDepartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GenDepartamento)) {
            return false;
        }
        GenDepartamento other = (GenDepartamento) object;
        if ((this.idDepartamento == null && other.idDepartamento != null) || (this.idDepartamento != null && !this.idDepartamento.equals(other.idDepartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GenDepartamento[ idDepartamento=" + idDepartamento + " ]";
    }


}
