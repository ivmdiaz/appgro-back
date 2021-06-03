package com.appgro.service;

import com.appgro.model.request.CategoriaRequest;
import com.appgro.model.response.CategoriaResponse;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;

import java.util.List;

public interface CategoriaService {


    /**
     * Función encargada de obtener las categorias registradas
     *
     * @return Respuesta genérica del servicio REST
     */
    EntidadResponse<List<CategoriaResponse>> listarCategorias();
    
    /**
     * Función encargada de registrar una nueva categoria
     *
     * @param etiqueta petición recibida por el servicio REST
     * @return Respuesta génerica del servicio REST
     */
	EstadoResponse guardarCategoria(CategoriaRequest request);
	
	/**
     * Función encargada de eliminar una categoria
     *
     * @return Respuesta genérica del servicio REST
     */
    EstadoResponse eliminarCategoria(Integer idCategoria);

}
