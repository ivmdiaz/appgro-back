package com.appgro.repository;

import com.appgro.model.entity.GenRecupContrasena;
import com.appgro.model.entity.GenRecupContrasenaPK;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenRecupContrasenaRepository extends JpaRepository<GenRecupContrasena, GenRecupContrasenaPK>,
        JpaSpecificationExecutor<GenRecupContrasena> {

    /**
     * Buscar un registro GenRecupContrasean segun un token dado
     *
     * @param token token tipo UUID de la base de datos.
     * @return obtiene un intento de recuperación de contraseña enviada por un usuario.
     */
    GenRecupContrasena findFirstByGenRecupContrasenaPKToken(String token);
    
    GenRecupContrasena findFirstByGenRecupContrasenaPKIdUsuarioAndEstadoCambio(Long idUsuario, String estado);
    
    List<GenRecupContrasena> findByGenRecupContrasenaPKIdUsuarioAndEstadoCambio(Long idUsuario, String estado);
}
