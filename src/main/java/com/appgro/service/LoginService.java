package com.appgro.service;

import com.appgro.model.request.AutenticacionRequest;
import com.appgro.model.request.RegistroUsuarioRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.LoginResponse;

public interface LoginService {

    /**
     * Función encargada de verificar los datos de autenticación de un usuario.
     *
     * @param peticion objeto que trae los datos para autenticar
     * @return retorna un estado general y una entidad de tipo GenUsuario
     */
    EntidadResponse<LoginResponse> autenticarUsuario(AutenticacionRequest peticion);

    /**
     * Función encargada de registrar un nuevo usuario en la tabla GEN_USUARIO
     *
     * @param peticion  objeto que trae los datos para autenticar un usuario
     * @param tipoActor objeto que trae el tipo de actor a registrar.
     * @return retorna un estado general de la petición
     */
    EstadoResponse registrarUsuario(RegistroUsuarioRequest peticion, Long tipoActor);

    /**
     * Función encargada de enviar un correo electrónico de recuperación de contraseña
     *
     * @param correo objeto que trae el correo para enviar el email de recuperación.
     * @return retorna un estado general de la petición
     */
    EstadoResponse enviarCorreoRecuperacionContrasena(String correo);

}
