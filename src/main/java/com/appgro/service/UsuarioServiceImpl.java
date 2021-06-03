package com.appgro.service;

import com.appgro.exception.NegocioException;
import com.appgro.model.entity.GenUsuario;
import com.appgro.model.entity.GenUsuarioUbicacion;
import com.appgro.model.request.ActualizarUsuarioRequest;
import com.appgro.model.request.IdentificadorRequest;
import com.appgro.model.request.RegistroDomicilioRequest;
import com.appgro.model.request.RegistroUsuarioRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.repository.GenUsuarioRepository;
import com.appgro.repository.GenUsuarioUbicacionRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    /**
     * Servicios de entidad de base de datos
     */
    private final GenUsuarioRepository mUsuarioRepository;
    private final GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository;

    public UsuarioServiceImpl(GenUsuarioRepository mUsuarioRepository,
                              GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository) {
        this.mUsuarioRepository = mUsuarioRepository;
        this.mGenUsuarioUbicacionRepository = mGenUsuarioUbicacionRepository;
    }

    /**
     * @see UsuarioService#guardarUsuario(RegistroUsuarioRequest)
     */
    @Override
    public EstadoResponse guardarUsuario(RegistroUsuarioRequest peticion) {
        try {
            //Obtenemos el id usuario del JWT @NotNull
            Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

            if (idUsuario != null) {
                //Obtenemos el usuario en base de datos para copiar la clave:
                GenUsuario mUsuario = mUsuarioRepository.findById(idUsuario).orElse(null);
                if (mUsuario != null) {
                    mUsuario.actualizar(peticion);
                    mUsuarioRepository.save(mUsuario);
                    return R0044;
                } else {
                    return R0045;
                }
            } else {
                return R0045;
            }
        } catch (NegocioException ex) {
            return negocioExceptiontoEstadoResponse(ex);
        } catch (Exception ex) {
            return exceptiontoEstadoResponse(ex);
        }
    }

    /**
     * @see UsuarioService#guardarDomicilio(RegistroDomicilioRequest)
     */
    @Override
    public EstadoResponse guardarDomicilio(RegistroDomicilioRequest peticion) {
        try {
            //Obtenemos el id usuario del JWT @NotNull
            Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());
            if (idUsuario != null) {
                GenUsuarioUbicacion mDomicilio = new GenUsuarioUbicacion(peticion);
                if (peticion.getIdUbicacion() != null) {
                    GenUsuarioUbicacion mDomicilioAntiguo = mGenUsuarioUbicacionRepository.findFirstByIdUbicacionAndIdUsuario(
                            peticion.getIdUbicacion(), idUsuario
                    );
                    mDomicilio.setPredeterminado(mDomicilioAntiguo.getPredeterminado());
                }
                mDomicilio.setIdUsuario(idUsuario);

                boolean existeUsuarioDomicilio = mGenUsuarioUbicacionRepository.existsByIdUsuario(idUsuario);
                if (!existeUsuarioDomicilio) {
                    mDomicilio.setPredeterminado(Boolean.TRUE);
                }
                mGenUsuarioUbicacionRepository.save(mDomicilio);
                return R0027;
            } else {
                return R0028;
            }
        } catch (NegocioException ex) {
            return negocioExceptiontoEstadoResponse(ex);
        } catch (Exception ex) {
            return exceptiontoEstadoResponse(ex);
        }
    }

    /**
     * @see UsuarioService#obtenerDomicilios()
     */
    @Override
    public EntidadResponse<List<GenUsuarioUbicacion>> obtenerDomicilios() {
        EntidadResponse<List<GenUsuarioUbicacion>> respuesta = new EntidadResponse<>();
        try {

            //Obtenemos el id usuario del JWT @NotNull
            Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

            if (idUsuario != null) {
                List<GenUsuarioUbicacion> domicilios =
                        mGenUsuarioUbicacionRepository.findAllByIdUsuario(idUsuario);
                respuesta.setEntidad(domicilios);
                respuesta.setEstado(R0025);
            } else {
                respuesta.setEstado(R0026);
            }
        } catch (NegocioException ex) {
            respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
        } catch (Exception ex) {
            respuesta.setEstado(exceptiontoEstadoResponse(ex));
        }
        return respuesta;
    }

    @Override
    public EstadoResponse eliminarDomicilio(Long idDomicilio) {
        try {
            //Obtenemos el id usuario del JWT @NotNull
            Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

            //Obtenemos el domicilio relacionado el identificador y el usuario.
            GenUsuarioUbicacion mDomicilio = mGenUsuarioUbicacionRepository
                    .findFirstByIdUbicacionAndIdUsuario(idDomicilio, idUsuario);

            if (mDomicilio != null) {
                mGenUsuarioUbicacionRepository.delete(mDomicilio);
                return R0029;
            } else {
                return R0030;
            }
        } catch (NegocioException ex) {
            return negocioExceptiontoEstadoResponse(ex);
        } catch (Exception ex) {
            return exceptiontoEstadoResponse(ex);
        }
    }

    /**
     * @see UsuarioService#seleccionarDomicilio(IdentificadorRequest)
     */
    @Override
    public EstadoResponse seleccionarDomicilio(IdentificadorRequest peticion) {
        try {
            //Obtenemos el id usuario del JWT @NotNull
            Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

            //Obtenemos el domicilio relacionado el identificador y el usuario.
            GenUsuarioUbicacion mDomicilio = mGenUsuarioUbicacionRepository
                    .findFirstByIdUbicacionAndIdUsuario(peticion.getId(), idUsuario);

            if (mDomicilio != null) {
                //Limpiamos el campo predeterminado de ubicaciones del usuario.
                mGenUsuarioUbicacionRepository.limpiarUbicacionPredeterminadaUsuario(idUsuario);

                //Asignamos el estado predeterminado a la ubicación seleccionada.
                mDomicilio.setPredeterminado(Boolean.TRUE);
                mGenUsuarioUbicacionRepository.save(mDomicilio);
                return R0038;
            } else {
                return R0039;
            }
        } catch (NegocioException ex) {
            return negocioExceptiontoEstadoResponse(ex);
        } catch (Exception ex) {
            return exceptiontoEstadoResponse(ex);
        }
    }

    /**
     * Función encargada de actualizar un usuario en la tabla GEN_USUARIO
     *
     * @param peticion objeto que trae los datos para autenticar un usuario
     * @return retorna un estado general de la petición
     */
    @Override
    public EstadoResponse actualizarUsuario(ActualizarUsuarioRequest peticion) {
        try {

            Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());
            peticion.setIdUsuario(idUsuario);
            
            //Mapeamos petición a entidad
            GenUsuario usuarioAntiguo = mUsuarioRepository.findFirstByIdUsuario(idUsuario);
            GenUsuario mUsuario = new GenUsuario(peticion);
            mUsuario.setPerfiles(usuarioAntiguo.getPerfiles());
            
            if(StringUtils.isEmpty(peticion.getContrasena())){
                mUsuario.setContrasena(usuarioAntiguo.getContrasena());
            }
            //Guardamos registro en base de datos
            mUsuarioRepository.save(mUsuario);

            //Asignamos variables de estado
            return R0047;

        } catch (NegocioException ex) {
            return negocioExceptiontoEstadoResponse(ex);
        } catch (Exception ex) {
            return exceptiontoEstadoResponse(ex);
        }
    }
}

