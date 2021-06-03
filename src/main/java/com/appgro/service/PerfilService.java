package com.appgro.service;

import com.appgro.model.response.EstadoResponse;

public interface PerfilService {

    /**
     * Función encargada de relacionar un perfil a un usuario especifico.
     *
     * @param idUsuario  identificador del usuario a configurar.
     * @param idPerfil identificador de perfil a asignar.
     * @return retorna un estado general de la petición
     */
    EstadoResponse asignarPerfilUsuario(Long idUsuario, Long idPerfil);
}
