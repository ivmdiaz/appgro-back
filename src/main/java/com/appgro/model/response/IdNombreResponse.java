package com.appgro.model.response;

import java.io.Serializable;

public class IdNombreResponse implements Serializable {

    /**
     * Object:{Long, Integer, String}
     **/
    private Object id;
    private String nombre;

    public IdNombreResponse(Object id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
