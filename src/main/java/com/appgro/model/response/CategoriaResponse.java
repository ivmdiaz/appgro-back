package com.appgro.model.response;

import com.appgro.model.entity.GenCategoriaProducto;

public class CategoriaResponse {

    private Integer idCategoria;
    private String categoria;
    private String imagen;

    public CategoriaResponse(GenCategoriaProducto bd) {
        this.idCategoria = bd.getIdCategoria();
        this.categoria = bd.getCategoria();
        if(bd.getImagen() != null){
            this.setImagen(bd.getImagen());
        }
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
