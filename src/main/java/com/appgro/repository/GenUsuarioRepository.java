package com.appgro.repository;

import com.appgro.model.entity.GenUsuario;
import com.appgro.model.response.UsuarioResponse;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface GenUsuarioRepository extends JpaRepository<GenUsuario, Long>,
        JpaSpecificationExecutor<GenUsuario> {

    /**
     * Buscar un registro de usuario segun su correo y contraseña
     *
     * @param correo     correo electronico o username del usuario
     * @param contrasena contraseña en SHA512 del usuario
     * @return un objeto de tipo GenUsuario
     */
    GenUsuario findFirstByCorreoAndContrasena(String correo, String contrasena);

    /**
     * Buscar un registro de usuario segun su correo electrónico
     *
     * @param correo correo electrónico o username del usuario
     * @return un objeto de tipo GenUsuario
     */
    GenUsuario findFirstUsuarioByCorreo(String correo);

    /**
     * Buscar un identificador de usuario segun su correo electrónico
     *
     * @param correo correo electrónico o username del usuario
     * @return un objeto de tipo GenUsuario
     */
    @Query("SELECT u.idUsuario FROM GenUsuario u WHERE u.correo = ?1 ")
    Long findIdUsuarioByCorreo(String correo);

    /**
     * Buscar un usuario por su llave primaria
     *
     * @param idUsuario identificador primario del usuario
     * @return un objeto de tipo GenUsuario
     */
    GenUsuario findFirstByIdUsuario(Long idUsuario);
    
    @Query("SELECT NEW com.appgro.model.response.UsuarioResponse(u) FROM GenUsuario u WHERE u.estado = ?1 ")
    List<UsuarioResponse> findByEstado(final String estado);
    
    @Query("SELECT NEW com.appgro.model.response.UsuarioResponse(u) FROM GenUsuario u")
    List<UsuarioResponse> findByResponse();
    
    @Query("select count(u) > 0 from GenUsuario u INNER JOIN u.perfiles p where u.idUsuario <> ?1 "
    		+ " AND p.idPerfil = 1")
    boolean existUsuarioPerfilAdministrador(Long idUsuario);
}
