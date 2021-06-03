package com.appgro.service;

import com.appgro.exception.NegocioException;
import com.appgro.model.entity.GenEtiqueta;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.IdNombreResponse;
import com.appgro.repository.GenEtiquetaRepository;
import com.appgro.repository.GenProductoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

@Service
public class EtiquetaServiceImpl implements EtiquetaService {

    private final GenEtiquetaRepository mGenEtiquetaRepository;
    private final GenProductoRepository mGenProductoRepository;

	public EtiquetaServiceImpl(GenEtiquetaRepository mGenEtiquetaRepository,
			GenProductoRepository mGenProductoRepository) {
		this.mGenEtiquetaRepository = mGenEtiquetaRepository;
		this.mGenProductoRepository = mGenProductoRepository;
	}


    /**
     * @see EtiquetaService#guardarEtiqueta(IdNombreResponse)
     */
    @Override
    public EstadoResponse guardarEtiqueta(IdNombreResponse etiqueta) {
        try {
        	
        	GenEtiqueta mEtiqueta = new GenEtiqueta();
        	
        	if(Objects.nonNull(etiqueta.getId())) {
        		mEtiqueta = mGenEtiquetaRepository.findById(Integer.parseInt(String.valueOf(etiqueta.getId()))).orElse(null);
        	}
        	
            if (Objects.nonNull(mEtiqueta)) {
                mEtiqueta.setEtiqueta(etiqueta.getNombre().trim());
                mEtiqueta = mGenEtiquetaRepository.save(mEtiqueta);
                return R0059;
            } 
            
            return R0060;

        } catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
    }

    @Override
    public EntidadResponse<List<GenEtiqueta>> listarEtiquetas() {
        EntidadResponse<List<GenEtiqueta>> respuesta = new EntidadResponse<>();
        try {
            List<GenEtiqueta> etiquetas = mGenEtiquetaRepository.findAll();
            respuesta.setEntidad(etiquetas);
            respuesta.setEstado(R0023);

        } catch (NegocioException ex) {
            respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
        } catch (Exception ex) {
            respuesta.setEstado(exceptiontoEstadoResponse(ex));
        }
        return respuesta;
    }


	@Override
	public EstadoResponse eliminarEtiqueta(Integer idEtiqueta) {
		try {
		
			Optional<GenEtiqueta> mEtiqueta = mGenEtiquetaRepository.findById(idEtiqueta);

			if (Objects.nonNull(mEtiqueta.get()) && !mGenProductoRepository.existByIdEtiqueta(idEtiqueta)) {
				mGenEtiquetaRepository.delete(mEtiqueta.get());
				return R0057;
			} else {
				return R0058;
			}

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}
}
