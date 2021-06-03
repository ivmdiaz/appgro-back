package com.appgro.service.administracion;

import com.appgro.model.entity.GenUsuarioUbicacion;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.IdNombreResponse;

import java.util.List;

public interface ParametrizacionService {

    /**
     * Función encargada de traer un domicilio por id:
     * @param idDomicilio identificador del domicilio.
     * @return
     */
    EntidadResponse<GenUsuarioUbicacion> domicilioPorId(Long idDomicilio);

    /**
     * Función encargada de traer todos los paises
     * @return
     */
    EntidadResponse<List<IdNombreResponse>> obtenerPaises();

    /**
     * Función encargada de traer todos los departamentos de un pais
     * @return
     */
    EntidadResponse<List<IdNombreResponse>> obtenerDepartamentosPorPais(Integer idPais);

    /**
     * Función encargada de traer todas las ciudades de un departamento
     * @return
     */
    EntidadResponse<List<IdNombreResponse>> obtenerCiudadesPorDepartamento(Integer idDepartamento);
}
