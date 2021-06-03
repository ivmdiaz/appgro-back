package com.appgro.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "gen_recup_contrasena")
public class GenRecupContrasena implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ESTADO_TOKEN_PROCESO = "PROCESO";
    public static final String ESTADO_TOKEN_TERMINADO = "TERMINADO";
    public static final String ESTADO_TOKEN_INHABILITADO = "INHABILITADO";


    @EmbeddedId
    protected GenRecupContrasenaPK genRecupContrasenaPK;

    @Basic(optional = false)
    @Column(name = "EXPIRACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiracion;

    @Column(name = "CONTRASENA_NUEVA")
    private String contrasenaNueva;

    @Column(name = "ESTADO_CAMBIO")
    private String estadoCambio;

    public GenRecupContrasena() {
    }

    public GenRecupContrasena(GenRecupContrasenaPK genRecupContrasenaPK) {
        this.genRecupContrasenaPK = genRecupContrasenaPK;
    }

    public GenRecupContrasena(long idUsuario, String token) {
        this.genRecupContrasenaPK = new GenRecupContrasenaPK(idUsuario, token);
    }

    public GenRecupContrasenaPK getGenRecupContrasenaPK() {
        return genRecupContrasenaPK;
    }

    public void setGenRecupContrasenaPK(GenRecupContrasenaPK genRecupContrasenaPK) {
        this.genRecupContrasenaPK = genRecupContrasenaPK;
    }

    public Date getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(Date expiracion) {
        this.expiracion = expiracion;
    }

    public String getContrasenaNueva() {
        return contrasenaNueva;
    }

    public void setContrasenaNueva(String contrasenaNueva) {
        this.contrasenaNueva = contrasenaNueva;
    }

    public String getEstadoCambio() {
        return estadoCambio;
    }

    public void setEstadoCambio(String estadoCambio) {
        this.estadoCambio = estadoCambio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (genRecupContrasenaPK != null ? genRecupContrasenaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GenRecupContrasena)) {
            return false;
        }
        GenRecupContrasena other = (GenRecupContrasena) object;
        return (this.genRecupContrasenaPK != null || other.genRecupContrasenaPK == null)
                && (this.genRecupContrasenaPK == null
                || this.genRecupContrasenaPK.equals(other.genRecupContrasenaPK));
    }

    @Override
    public String toString() {
        return "entidades.GenRecupContrasena[ genRecupContrasenaPK=" + genRecupContrasenaPK + " ]";
    }
}
