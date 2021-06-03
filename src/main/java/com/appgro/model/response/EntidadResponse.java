package com.appgro.model.response;

/**
 * Respuesta genérica de servicios REST
 * @param <E> tipo de entidad que retorna la petición
 */
public class EntidadResponse<E> {

    private EstadoResponse estado;
    private E entidad;

    /**
     * Constructor vacío.
     */
    public EntidadResponse() {
    }

    /**
     * Metodo Getter del atributo estado
     * @return obtiene un estado general de la petición realizada
     */
    public EstadoResponse getEstado() {
        if(estado == null){
            estado = new EstadoResponse();
        }
        return estado;
    }

    /**
     * Metodo Setter del atributo estado
     * @param estado estado general de la petición realizada.
     */
    public void setEstado(EstadoResponse estado) {
        this.estado = estado;
    }

    /**
     * Metodo Getter del atributo entidad
     * @return devuelve una entidad genérica
     */
    public E getEntidad() {
        return entidad;
    }

    /**
     * Metodo Setter del atributo entidad
     * @param entidad objeto  entidad genérica
     */
    public void setEntidad(E entidad) {
        this.entidad = entidad;
    }

}
