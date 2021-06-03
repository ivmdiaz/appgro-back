package com.appgro.repository;

import com.appgro.model.entity.GenUsuarioUbicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GenUsuarioUbicacionRepository extends JpaRepository<GenUsuarioUbicacion, Long>,
        JpaSpecificationExecutor<GenUsuarioUbicacion> {

    /**
     * Función encargada de obtener los domicilios de un usuario.
     *
     * @param idUsuario identificador de usuario a filtrar
     * @return domicilios filtrados segun parámetro
     */
    List<GenUsuarioUbicacion> findAllByIdUsuario(Long idUsuario);

    /**
     * Función encargadade obtener un domicilio en especifico.
     *
     * @param idUbicacion identificador de domicilio
     * @param idUsuario   identificador de usuario
     * @return domicilio con el filtro segun los parámetros.
     */
    GenUsuarioUbicacion findFirstByIdUbicacionAndIdUsuario(Long idUbicacion, Long idUsuario);

    /**
     * Función encargada si buscar si existen domicilios parametrizados para el usuario.
     * @param idUsuario identificador de usuario
     * @return
     */
    Boolean existsByIdUsuario(Long idUsuario);

    /**
     * Función encargada de actualizar el campo predeterminado = falso para todos los
     * domicilios de un usuario.
     * @param idUsuario identificador de usuario
     */
    @Transactional
    @Modifying
    @Query("UPDATE GenUsuarioUbicacion u SET u.predeterminado = FALSE where u.idUsuario = ?1")
    void limpiarUbicacionPredeterminadaUsuario(Long idUsuario);

    /**
     * Función encargada de traer la ubicación predeterminada del usuario
     * @param idUsuario
     * @param predeterminado
     * @return
     */
    GenUsuarioUbicacion findFirstByIdUsuarioAndPredeterminado(Long idUsuario, Boolean predeterminado);
}
