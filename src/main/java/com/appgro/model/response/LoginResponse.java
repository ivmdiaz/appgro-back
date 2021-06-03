package com.appgro.model.response;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private UsuarioResponse usuario;

    private String jwt;

    public UsuarioResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponse usuario) {
        this.usuario = usuario;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
