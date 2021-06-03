package com.appgro.model.request;

import java.io.Serializable;

public class CorreoRecuperarContrasenaRequest implements Serializable {

    private String correo;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
