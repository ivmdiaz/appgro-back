package com.appgro.model.request;

import java.io.Serializable;


public class AsociarPerfilUsuarioRequest implements Serializable {

    private Long idUsuario;

    private Long idPerfil;

    public AsociarPerfilUsuarioRequest(Long idUsuario, Long idPerfil) {
        this.idUsuario = idUsuario;
        this.idPerfil = idPerfil;
    }

    public AsociarPerfilUsuarioRequest() {
        super();
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }
}
