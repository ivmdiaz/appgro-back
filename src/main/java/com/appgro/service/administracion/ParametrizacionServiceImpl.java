package com.appgro.service.administracion;

import com.appgro.exception.NegocioException;
import com.appgro.model.entity.GenUsuarioUbicacion;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.repository.GenUsuarioUbicacionRepository;
import com.appgro.repository.ParametrizacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

@Service
public class ParametrizacionServiceImpl implements ParametrizacionService {

    private final GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository;

    private final ParametrizacionRepository mParametrizacionRepository;

    public ParametrizacionServiceImpl(GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository,
                                      ParametrizacionRepository mParametrizacionRepository) {
        this.mGenUsuarioUbicacionRepository = mGenUsuarioUbicacionRepository;
        this.mParametrizacionRepository = mParametrizacionRepository;
    }

    /**
     * @see ParametrizacionService#domicilioPorId(Long)
     */
    @Override
    public EntidadResponse<GenUsuarioUbicacion> domicilioPorId(final Long idDomicilio) {
        final EntidadResponse<GenUsuarioUbicacion> respuesta = new EntidadResponse<>();
        try {
            respuesta.setEntidad(mGenUsuarioUbicacionRepository.findById(idDomicilio).orElse(null));
            respuesta.setEstado(R0040);
        } catch (NegocioException ex) {
            respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
        } catch (Exception ex) {
            respuesta.setEstado(exceptiontoEstadoResponse(ex));
        }
        return respuesta;
    }

    /**
     * @see ParametrizacionService#obtenerPaises()
     */
    @Override
    public EntidadResponse<List<IdNombreResponse>> obtenerPaises() {
        final EntidadResponse<List<IdNombreResponse>> respuesta = new EntidadResponse<>();
        try {
            respuesta.setEntidad(mParametrizacionRepository.obtenerPaises());
            respuesta.setEstado(R0041);
        } catch (NegocioException ex) {
            respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
        } catch (Exception ex) {
            respuesta.setEstado(exceptiontoEstadoResponse(ex));
        }
        return respuesta;
    }

    /**
     * @see ParametrizacionService#obtenerDepartamentosPorPais(Integer)
     */
    @Override
    public EntidadResponse<List<IdNombreResponse>> obtenerDepartamentosPorPais(final Integer idPais) {
        final EntidadResponse<List<IdNombreResponse>> respuesta = new EntidadResponse<>();
        try {
            respuesta.setEntidad(mParametrizacionRepository.obtenerDepartamentosPorPais(idPais));
            respuesta.setEstado(R0042);
        } catch (NegocioException ex) {
            respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
        } catch (Exception ex) {
            respuesta.setEstado(exceptiontoEstadoResponse(ex));
        }
        return respuesta;
    }

    /**
     * @see ParametrizacionService#obtenerCiudadesPorDepartamento(Integer)
     */
    @Override
    public EntidadResponse<List<IdNombreResponse>> obtenerCiudadesPorDepartamento(final Integer idDepartamento) {
        final EntidadResponse<List<IdNombreResponse>> respuesta = new EntidadResponse<>();
        try {
            respuesta.setEntidad(mParametrizacionRepository.obtenerCiudadesPorDepartamento(idDepartamento));
            respuesta.setEstado(R0043);
        } catch (NegocioException ex) {
            respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
        } catch (Exception ex) {
            respuesta.setEstado(exceptiontoEstadoResponse(ex));
        }
        return respuesta;
    }
}
