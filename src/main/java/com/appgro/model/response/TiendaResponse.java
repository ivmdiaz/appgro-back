package com.appgro.model.response;

import com.appgro.model.entity.GenTienda;

import java.util.Date;

public class TiendaResponse {

    private Integer idTienda;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String telefono1;
    private String telefono2;
    private String correo;
    private Double latitud;
    private Double longitud;
    private Date regDate;

    private Long idUsuario;

    private String ciudad;
    private String departamento;

    private String imagen;

    public TiendaResponse(GenTienda bd){
        this.setIdTienda(bd.getIdTienda());
        this.setNombre(bd.getNombre());
        this.setDescripcion(bd.getDescripcion());
        this.setDireccion(bd.getDireccion());
        this.setTelefono1(bd.getTelefono1());
        this.setTelefono2(bd.getTelefono2());
        this.setCorreo(bd.getCorreo());
        this.setRegDate(bd.getRegDate());
        this.setLatitud(bd.getLatitud());
        this.setLongitud(bd.getLongitud());
        this.setIdUsuario(bd.getIdUsuario());

        if(bd.getCiudad() != null){
            this.setCiudad(bd.getCiudad().getNombre());
            this.setDepartamento(bd.getCiudad().getIdDepartamento().getNombre());
        }

        if(bd.getImagen() != null){
            this.setImagen(bd.getImagen());
        }

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
