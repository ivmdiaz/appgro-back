package com.appgro.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "gen_etiqueta")
public class GenEtiqueta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ETIQUETA")
    private Integer idEtiqueta;

    @Column(name = "ETIQUETA")
    private String etiqueta;

    @Column(name = "REG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    public GenEtiqueta() {
    }

    public GenEtiqueta(Integer idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public Integer getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(Integer idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEtiqueta != null ? idEtiqueta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GenEtiqueta)) {
            return false;
        }
        GenEtiqueta other = (GenEtiqueta) object;
        if ((this.idEtiqueta == null && other.idEtiqueta != null) || (this.idEtiqueta != null && !this.idEtiqueta.equals(other.idEtiqueta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GenEtiqueta[ idEtiqueta=" + idEtiqueta + " ]";
    }
}
