package com.appgro.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "gen_perfil")
public class GenPerfil implements Serializable {

    // Constantes
    public static final Long ID_PERFIL_ADMINISTRADOR = 1L;
    public static final Long ID_PERFIL_COMPRADOR = 2L;
    public static final Long ID_PERFIL_VENDEDOR = 3L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerfil;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    //@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "gen_perfil_rol",
            joinColumns = {@JoinColumn(name = "ID_PERFIL")},
            inverseJoinColumns = {@JoinColumn(name = "ID_ROL")})
    private List<GenRol> roles;

    public GenPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }

    public GenPerfil() {
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
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

    public List<GenRol> getRoles() {
        return roles;
    }

    public void setRoles(List<GenRol> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GenPerfil)) {
            return false;
        }
        GenPerfil other = (GenPerfil) object;
        if ((this.idPerfil == null && other.idPerfil != null)
                || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }
}
