package com.appgro.model.request;

import java.io.Serializable;

public class RecuperarContrasenaRequest implements Serializable {

    private String token;

    private String nuevaConstrasena;

    private String nuevaContrasenaConfirmacion;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNuevaConstrasena() {
        return nuevaConstrasena;
    }

    public void setNuevaConstrasena(String nuevaConstrasena) {
        this.nuevaConstrasena = nuevaConstrasena;
    }

    public String getNuevaContrasenaConfirmacion() {
        return nuevaContrasenaConfirmacion;
    }

    public void setNuevaContrasenaConfirmacion(String nuevaContrasenaConfirmacion) {
        this.nuevaContrasenaConfirmacion = nuevaContrasenaConfirmacion;
    }
}
