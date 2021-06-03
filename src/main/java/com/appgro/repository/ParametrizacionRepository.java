package com.appgro.repository;

import com.appgro.config.ReadOnlyRepository;
import com.appgro.model.entity.GenPais;
import com.appgro.model.response.IdNombreResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParametrizacionRepository extends ReadOnlyRepository<GenPais> {

    /**
     * Función para obtener los paises parametrizados
     *
     * @return
     */
    @Query("SELECT new com.appgro.model.response.IdNombreResponse(p.idPais, p.nombre) FROM GenPais p ")
    List<IdNombreResponse> obtenerPaises();

    /**
     * Función para obtener los departamentos de un pais
     *
     * @return
     */
    @Query("SELECT new com.appgro.model.response.IdNombreResponse(d.idDepartamento, d.nombre) "
            + " FROM GenDepartamento d WHERE d.idPais.idPais = ?1 ")
    List<IdNombreResponse> obtenerDepartamentosPorPais(Integer idPais);

    /**
     * Función para obtener los departamentos de un pais
     *
     * @return
     */
    @Query("SELECT new com.appgro.model.response.IdNombreResponse(c.idCiudad, c.nombre) "
            + " FROM GenCiudad c WHERE c.idDepartamento.idDepartamento = ?1 ")
    List<IdNombreResponse> obtenerCiudadesPorDepartamento(Integer idDepartamento);

}
