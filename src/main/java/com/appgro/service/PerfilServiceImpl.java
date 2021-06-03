package com.appgro.service;

import com.appgro.exception.NegocioException;
import com.appgro.model.entity.GenPerfil;
import com.appgro.model.entity.GenUsuario;
import com.appgro.model.response.EstadoResponse;
import com.appgro.repository.GenUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

@Service
public class PerfilServiceImpl implements PerfilService {

    /**
     * Servicios de entidad de base de datos
     */
    private final GenUsuarioRepository mUsuarioRepository;

    public PerfilServiceImpl(GenUsuarioRepository mUsuarioRepository) {
        this.mUsuarioRepository = mUsuarioRepository;
    }

    /**
     * @see PerfilServiceImpl#asignarPerfilUsuario(Long, Long)
     */
    @Override
    public EstadoResponse asignarPerfilUsuario(Long idUsuario, Long idPerfil) {
        try {

            //Obtenemos usuario para realizar el cambio de clave.
            GenUsuario mGenUsuario = mUsuarioRepository.findFirstByIdUsuario(idUsuario);

            //Creamos objeto en memoria con el perfil
            GenPerfil mGenPerfil = new GenPerfil(idPerfil);

            //Si no tiene perfiles asignados lo inicializamos.
            mGenUsuario.setPerfiles(new ArrayList<>());

            //Si no existe el perfil lo asignamos
            if (!mGenUsuario.getPerfiles().contains(mGenPerfil)) {
                mGenUsuario.getPerfiles().add(mGenPerfil);

                //Actualizamos el objeto del usuario.
                mUsuarioRepository.save(mGenUsuario);
            }

            //Generamos salida.
            return R0012;

        } catch (NegocioException ex) {
            return negocioExceptiontoEstadoResponse(ex);
        } catch (Exception ex) {
            return exceptiontoEstadoResponse(ex);
        }
    }
}
