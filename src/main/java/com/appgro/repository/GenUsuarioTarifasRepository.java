package com.appgro.repository;

import com.appgro.model.entity.GenUsuarioTarifa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface GenUsuarioTarifasRepository
        extends JpaRepository<GenUsuarioTarifa, Long>, JpaSpecificationExecutor<GenUsuarioTarifa> {

    /**
     * Lista de tarifas por usuarios.
     *
     * @param idUsuario identificador del comprador.
     * @return
     */
    List<GenUsuarioTarifa> findByIdUsuarioOrderByRangoInicial(Long idUsuario);

    @Query("SELECT t FROM GenUsuarioTarifa t, GenUsuario u, GenTienda ti, GenProducto p " +
            "WHERE t.idUsuario = u.idUsuario AND ti.idUsuario = u.idUsuario AND p.idTienda = ti.idTienda AND p.idProducto = ?2 " +
            "AND t.rangoInicial <= ?1 AND ( t.rangoFinal IS NULL or t.rangoFinal >= ?1 )")
    GenUsuarioTarifa getTarifaByDistanciaAndProducto(Double distancia, Integer idProducto);

}
