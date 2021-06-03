package com.appgro.model.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EstadoResponse {

    private boolean exitoso;
    private String mensaje;
    private Date estampaTiempo;
    private List<String> errores;

    public boolean isExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getEstampaTiempo() {
        return estampaTiempo;
    }

    public void setEstampaTiempo(Date estampaTiempo) {
        this.estampaTiempo = estampaTiempo;
    }

    public List<String> getErrores() {
        if(errores == null){
            errores = new ArrayList<>();
        }
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }
}
