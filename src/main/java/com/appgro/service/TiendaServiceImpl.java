package com.appgro.service;

import com.appgro.exception.NegocioException;
import com.appgro.model.entity.GenTienda;
import com.appgro.model.entity.GenUsuarioUbicacion;
import com.appgro.model.request.RegistroTiendaRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.TiendaResponse;
import com.appgro.repository.GenProductoRepository;
import com.appgro.repository.GenTiendaRepository;
import com.appgro.repository.GenUsuarioRepository;
import com.appgro.repository.GenUsuarioUbicacionRepository;
import com.appgro.util.AppgroUtil;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

@Service
public class TiendaServiceImpl implements TiendaService {

    /**
     * Servicios de entidad de base de datos
     */
    private final GenUsuarioRepository mUsuarioRepository;
    private final GenTiendaRepository mGenTiendaRepository;
    private final GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository;
    private final GenProductoRepository mGenProductoRepository;

    public TiendaServiceImpl(GenUsuarioRepository mUsuarioRepository,
                             GenTiendaRepository mGenTiendaRepository, 
                             GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository,
                             GenProductoRepository mGenProductoRepository) {
        this.mUsuarioRepository = mUsuarioRepository;
        this.mGenTiendaRepository = mGenTiendaRepository;
        this.mGenUsuarioUbicacionRepository = mGenUsuarioUbicacionRepository;
        this.mGenProductoRepository = mGenProductoRepository;
    }

    /**
     * @see TiendaService#guardarTienda(RegistroTiendaRequest)
     */
    @Override
    public EstadoResponse guardarTienda(RegistroTiendaRequest peticion) {
        try {

            //Obtenemos el id usuario del JWT @NotNull
            Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

            //Mapeamos el objeto Peticion a la entidad.
            GenTienda mTienda = new GenTienda(peticion);
            mTienda.setIdUsuario(idUsuario);
            mTienda.setEstado(GenTienda.ESTADO_HABILITADA);

            //Guardamos el registro en base de datos:
            mGenTiendaRepository.save(mTienda);

            return R0013;

        } catch (NegocioException ex) {
            return negocioExceptiontoEstadoResponse(ex);
        } catch (Exception ex) {
            return exceptiontoEstadoResponse(ex);
        }
    }

    /**
     * @see TiendaService#listarTiendas(Integer, Integer)
     */
    @Override
    public EntidadResponse<List<TiendaResponse>> listarTiendas(Integer page, Integer limit) {
        EntidadResponse<List<TiendaResponse>> respuesta = new EntidadResponse<>();
        try {
            //Obtenemos las tiendas segun los parámetros.
            List<TiendaResponse> tiendas = mGenTiendaRepository.findAllLazy(PageRequest.of(page, limit));
            respuesta.setEntidad(tiendas);
            respuesta.setEstado(R0016);

        } catch (NegocioException ex) {
            respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
        } catch (Exception ex) {
            respuesta.setEstado(exceptiontoEstadoResponse(ex));
        }
        return respuesta;
    }

    /**
     * @see TiendaService#obtenerTiendasByCategoria(Integer)
     */
    @Override
    public EntidadResponse<List<TiendaResponse>> obtenerTiendasByCategoria(Integer idCategoria) {
        EntidadResponse<List<TiendaResponse>> respuesta = new EntidadResponse<>();
        try {
            //Obtenemos las tiendas segun los parámetros.
            List<TiendaResponse> tiendas = mGenTiendaRepository.findAllByCategoria(idCategoria);
            respuesta.setEntidad(tiendas);
            respuesta.setEstado(R0016);
        } catch (NegocioException ex) {
            respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
        } catch (Exception ex) {
            respuesta.setEstado(exceptiontoEstadoResponse(ex));
        }
        return respuesta;
    }

    /**
     * @see TiendaService#listarTiendas()
     */
    @Override
    public EntidadResponse<List<TiendaResponse>> listarTiendas() {
        EntidadResponse<List<TiendaResponse>> respuesta = new EntidadResponse<>();
        try {
            //Obtenemos las tiendas segun los parámetros.
            List<TiendaResponse> tiendas = mGenTiendaRepository.findAllLazy(null);
            respuesta.setEntidad(tiendas);
            respuesta.setEstado(R0016);
        } catch (NegocioException ex) {
            respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
        } catch (Exception ex) {
            respuesta.setEstado(exceptiontoEstadoResponse(ex));
        }
        return respuesta;
    }

    @Override
    public EntidadResponse<List<TiendaResponse>> listarTiendasCercanas() {
        EntidadResponse<List<TiendaResponse>> respuesta = new EntidadResponse<>();
        try {
            //Obtenemos el id usuario del JWT @NotNull
            final Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());
            final GenUsuarioUbicacion ubicacionActual = mGenUsuarioUbicacionRepository.findFirstByIdUsuarioAndPredeterminado(idUsuario, true);
            if (ubicacionActual == null) {
                throw new NegocioException("No tiene ubicación predeterminada");
            }
            HashMap<Double, TiendaResponse> mapTiendas = new HashMap<>();
            List<TiendaResponse> tiendas = mGenTiendaRepository.findAllLazy(null);
            tiendas.forEach((final TiendaResponse tienda) -> {
                double distancia = AppgroUtil.distanciaEntreDosCoordenadas(ubicacionActual.getLatitud(),
                        ubicacionActual.getLongitud(), tienda.getLatitud(), tienda.getLongitud());
                mapTiendas.put(distancia, tienda);
            });
            final List<TiendaResponse> tiendasOrdenadas = new ArrayList<>();
            mapTiendas.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                    .forEachOrdered(k -> tiendasOrdenadas.add(k.getValue()));
            respuesta.setEntidad(tiendasOrdenadas);
            respuesta.setEstado(R0050);
        } catch (NegocioException nex) {
            respuesta.setEstado(negocioExceptiontoEstadoResponse(nex));
        } catch (Exception e) {
            respuesta.setEstado(exceptiontoEstadoResponse(e));
        }
        return respuesta;
    }

    
	@Override
	public EstadoResponse eliminarTienda(Integer idTienda) {
		try {
			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());

			GenTienda mTienda = mGenTiendaRepository.findFirstByIdTiendaAndIdUsuario(idTienda, idUsuario);

			if (Objects.nonNull(mTienda) && mGenProductoRepository.findAllByIdTienda(idTienda).isEmpty()) {
				mGenTiendaRepository.delete(mTienda);
				return R0052;
			} else {
				return R0053;
			}

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}
	
}
