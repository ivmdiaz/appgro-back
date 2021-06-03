package com.appgro.model.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GenRecupContrasenaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private long idUsuario;

    @Basic(optional = false)
    @Column(name = "TOKEN")
    private String token;
    
    public GenRecupContrasenaPK() {
    }

    public GenRecupContrasenaPK(long idUsuario, String token) {
        this.idUsuario = idUsuario;
        this.token = token;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUsuario;
        hash += (token != null ? token.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GenRecupContrasenaPK)) {
            return false;
        }
        GenRecupContrasenaPK other = (GenRecupContrasenaPK) object;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return (this.token != null || other.token == null) &&
                (this.token == null || this.token.equals(other.token));
    }

    @Override
    public String toString() {
        return "GenRecupContrasenaPK[ idUsuario=" + idUsuario + ", token=" + token + " ]";
    }
}
