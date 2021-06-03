package com.appgro.service;

import com.appgro.model.request.RegistroTiendaRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.TiendaResponse;

import java.util.List;

public interface TiendaService {

    /**
     * Función encargada de registrar una tienda nueva.
     *
     * @param peticion petición recibida por el servicio REST
     * @return Respuesta génerica del servicio REST
     */
    EstadoResponse guardarTienda(RegistroTiendaRequest peticion);
    
    /**
     * Función encargada de registrar una tienda nueva.
     *
     * @param peticion petición recibida por el servicio REST
     * @return Respuesta génerica del servicio REST
     */
    EstadoResponse eliminarTienda(Integer idTienda);

    /**
     * Función encargada de obtener una lista de tiendas lazy (carga diferida)
     *
     * @param page  número de pagina a filtrar
     * @param limit cantidad de registros por página.
     * @return lista de tiendas con carga diferida
     */
    EntidadResponse<List<TiendaResponse>> listarTiendas(Integer page, Integer limit);

    /**
     * Función encargada de obtener una lista de tiendas por categoria.
     *
     * @param idCategoria identificador de categoria a filtrar
     * @return lista de tiendas con carga diferida
     */
    EntidadResponse<List<TiendaResponse>> obtenerTiendasByCategoria(Integer idCategoria);

    /**
     * Función encargada de obtener todas las tiendas
     *
     * @return lista de tiendas sin carga diferida
     */
    EntidadResponse<List<TiendaResponse>> listarTiendas();

    /**
     * Retorna las tiendas ordenadas por cercanía de acuerdo a la dirección actual del vendedor
     *
     * @return Lista de tiendas ordenadas
     */
    EntidadResponse<List<TiendaResponse>> listarTiendasCercanas();
}
