package com.appgro.service;

import com.appgro.model.entity.GenUsuarioUbicacion;
import com.appgro.model.request.ActualizarUsuarioRequest;
import com.appgro.model.request.IdentificadorRequest;
import com.appgro.model.request.RegistroDomicilioRequest;
import com.appgro.model.request.RegistroUsuarioRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;

import java.util.List;

public interface UsuarioService {

    /**
     * Función encargada de guardar un domicilio relacionado a el usuario
     *
     * @param peticion
     * @return
     */
    EstadoResponse guardarUsuario(RegistroUsuarioRequest peticion);

    /**
     * Función encargada de guardar un domicilio relacionado a el usuario
     *
     * @param peticion
     * @return
     */
    EstadoResponse guardarDomicilio(RegistroDomicilioRequest peticion);

    /**
     * Función encargada de obtener los domicilios relacionados a un usuario.
     *
     * @return
     */
    EntidadResponse<List<GenUsuarioUbicacion>> obtenerDomicilios();

    /**
     * Función encargada de eliminar un domicilio relacionado a el usuario
     *
     * @param idDomicilio id del domicilio a eliminar
     * @return
     */
    EstadoResponse eliminarDomicilio(Long idDomicilio);

    /**
     * Función encargada de seleccionar un domicilio relacionado a el usuario
     *
     * @param peticion
     * @return
     */
    EstadoResponse seleccionarDomicilio(IdentificadorRequest peticion);

    /**
     * Función encargada de actualizar un usuario en la tabla GEN_USUARIO
     *
     * @param peticion objeto que trae los datos para autenticar un usuario
     * @return retorna un estado general de la petición
     */
    EstadoResponse actualizarUsuario(ActualizarUsuarioRequest peticion);

}
