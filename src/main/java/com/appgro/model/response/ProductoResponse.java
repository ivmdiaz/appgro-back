package com.appgro.model.response;

import com.appgro.model.entity.GenEtiqueta;
import com.appgro.model.entity.GenProducto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoResponse {

    private Integer idProducto;
    private Integer idTienda;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Date regDate;
    private String imagen;
    private Integer cantidadDisponible;
    private String unidadMedida;

    private List<String> etiquetas;

    private String tipoProducto;

    public ProductoResponse(GenProducto bd) {
        this.setIdProducto(bd.getIdProducto());
        this.setIdTienda(bd.getIdTienda());
        this.setNombre(bd.getNombre());
        this.setDescripcion(bd.getDescripcion());
        this.setPrecio(bd.getPrecio());
        this.setTipoProducto(bd.getCategoria().getCategoria());
        this.setUnidadMedida(bd.getUnidadMedida());
        this.setCantidadDisponible(bd.getDisponibilidad());
        if (bd.getEtiquetas() != null) {
            final List<String> tempEtiquetas = bd.getEtiquetas()
                    .stream().map(GenEtiqueta::getEtiqueta)
                    .collect(Collectors.toList());
            this.setEtiquetas(tempEtiquetas);
        }

        if(bd.getImagen() != null){
            this.setImagen(bd.getImagen());
        }
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(Integer idTienda) {
        this.idTienda = idTienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
}
