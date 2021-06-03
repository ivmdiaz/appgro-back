package com.appgro.service;

import com.appgro.model.entity.GenEtiqueta;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.IdNombreResponse;

import java.util.List;

public interface EtiquetaService {

    /**
     * Función encargada de registrar una nueva etiqueta
     *
     * @param etiqueta petición recibida por el servicio REST
     * @return Respuesta génerica del servicio REST
     */
	EstadoResponse guardarEtiqueta(IdNombreResponse etiqueta);

    /**
     * Función encargada de obtener las etiquetas registradas
     *
     * @return Respuesta genérica del servicio REST
     */
    EntidadResponse<List<GenEtiqueta>> listarEtiquetas();
    
    /**
     * Función encargada de eliminar una etiqueta
     *
     * @return Respuesta genérica del servicio REST
     */
    EstadoResponse eliminarEtiqueta(Integer idEtiqueta);
}
