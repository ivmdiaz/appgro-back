package com.appgro.model.request;

import java.io.Serializable;

public class IdentificadorRequest implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
