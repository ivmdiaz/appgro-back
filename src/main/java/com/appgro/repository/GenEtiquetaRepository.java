package com.appgro.repository;

import com.appgro.model.entity.GenEtiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenEtiquetaRepository extends JpaRepository<GenEtiqueta, Integer>,
        JpaSpecificationExecutor<GenEtiqueta> {

    /**
     * Obtiene la primer coincidencia de la etiqueta a filtrar
     * @param etiqueta valor recibido por servicio REST
     * @return etiqueta relacionada al filtro
     */
    GenEtiqueta findFirstByEtiqueta(String etiqueta);

}
