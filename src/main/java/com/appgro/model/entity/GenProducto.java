package com.appgro.model.entity;

import com.appgro.model.request.EliminarProductoRequest;
import com.appgro.model.request.RegistroProductoRequest;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "gen_producto")
public class GenProducto {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Integer idProducto;

    @Column(name = "ID_TIENDA")
    private Integer idTienda;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "PRECIO")
    private Double precio;

    @Column(name = "DISPONIBILIDAD")
    private Integer disponibilidad;

    @Column(name = "REG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @Column(name = "IMAGEN")
    @Basic(fetch = FetchType.LAZY)
    private String imagen;

    @Column(name = "UNIDAD_MEDIDA")
    private String unidadMedida;

    @ManyToMany()
    @JoinTable(name = "gen_producto_etiqueta",
            joinColumns = {@JoinColumn(name = "ID_PRODUCTO")},
            inverseJoinColumns = {@JoinColumn(name = "ID_ETIQUETA")})
    private List<GenEtiqueta> etiquetas = new ArrayList<>();

    @JoinColumn(name = "ID_CATEGORIA")
    @ManyToOne()
    private GenCategoriaProducto categoria;

    public GenProducto() {
    }

    public GenProducto(RegistroProductoRequest peticion) {
        this.setIdProducto(peticion.getIdProducto());
        this.setIdTienda(peticion.getIdTienda());
        this.setNombre(peticion.getNombre());
        this.setDescripcion(peticion.getDescripcion());
        this.setPrecio(peticion.getPrecio());
        this.setCategoria(new GenCategoriaProducto(peticion.getCategoria()));
        this.setDisponibilidad(peticion.getDisponibilidad());
        this.setUnidadMedida(peticion.getUnidadMedida());
        if (peticion.getEtiquetas() != null) {
            final List<GenEtiqueta> tempEtiquetas = peticion.getEtiquetas()
                    .stream().map(id -> new GenEtiqueta(id))
                    .collect(Collectors.toList());
            this.setEtiquetas(tempEtiquetas);
        }
        this.setImagen(peticion.getImagen());
    }

    public GenProducto(EliminarProductoRequest peticion) {
        this.setIdProducto(peticion.getIdProducto());
        this.setIdTienda(peticion.getIdTienda());
    }

    public GenProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public GenProducto(Integer idProducto, Date regDate) {
        this.idProducto = idProducto;
        this.regDate = regDate;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
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

    public Integer getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public List<GenEtiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<GenEtiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public Integer getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(Integer idTienda) {
        this.idTienda = idTienda;
    }

    public GenCategoriaProducto getCategoria() {
        return categoria;
    }

    public void setCategoria(GenCategoriaProducto categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GenProducto)) {
            return false;
        }
        GenProducto other = (GenProducto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GenProducto[ idProducto=" + idProducto + " ]";
    }

}
