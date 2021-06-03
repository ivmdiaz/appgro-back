package com.appgro.service;

import com.appgro.comun.EmailComponent;
import com.appgro.config.SecurityConfig;
import com.appgro.exception.NegocioException;
import com.appgro.model.entity.*;
import com.appgro.model.request.AutenticacionRequest;
import com.appgro.model.request.CorreoPlantillaRequest;
import com.appgro.model.request.RegistroUsuarioRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.EstadoResponse;
import com.appgro.model.response.LoginResponse;
import com.appgro.model.response.UsuarioResponse;
import com.appgro.repository.GenRecupContrasenaRepository;
import com.appgro.repository.GenUsuarioRepository;
import com.appgro.repository.GenUsuarioUbicacionRepository;
import com.appgro.util.EncriptadorAES;
import com.appgro.util.RandomString;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.appgro.util.AppgroUtil.*;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

@Service
public class LoginServiceImpl implements LoginService {
	
	private final EncriptadorAES aes = new EncriptadorAES();

    /**
     * Servicios para acceso a la base de datos:
     */
    private final GenUsuarioRepository mUsuarioRepository;
    private final GenRecupContrasenaRepository mRecupContrasenaRepository;
    private final GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository;

    /**
     * Servicios de negocio
     */
    private final EmailComponent mEmailService;
    private final PerfilService mPerfilService;

    public LoginServiceImpl(GenUsuarioRepository mUsuarioRepository,
                            GenRecupContrasenaRepository mRecupContrasenaRepository,
                            GenUsuarioUbicacionRepository mGenUsuarioUbicacionRepository,
                            EmailComponent mEmailService, PerfilService mPerfilService) {
        this.mUsuarioRepository = mUsuarioRepository;
        this.mRecupContrasenaRepository = mRecupContrasenaRepository;
        this.mGenUsuarioUbicacionRepository = mGenUsuarioUbicacionRepository;
        this.mEmailService = mEmailService;
        this.mPerfilService = mPerfilService;
    }

    /**
     * @see LoginService#autenticarUsuario(AutenticacionRequest)
     */
    @Override
    public EntidadResponse<LoginResponse> autenticarUsuario(AutenticacionRequest peticion) {
        EntidadResponse<LoginResponse> respuesta = new EntidadResponse<>();
        try {

            //Usamos algoritmo criptografico SHA512 para la contraseña
            String contrasenaSinHash = peticion.getContrasena().trim();
            String contrasenaConHash = DigestUtils.sha512Hex(contrasenaSinHash);
            
            String correoConAES = aes.encriptar(peticion.getUsuario().trim());

            //Consultamos el usuario (correo) y contraseña con SHA512 en la base de datos.
            GenUsuario mUsuario = obtenerUsuario(correoConAES, contrasenaConHash);

            //Si obtenemos resultados, la autenticación fue correcta
            if (mUsuario != null) {

                //Traemos el domicilio predeterminado
                GenUsuarioUbicacion domicilio = mGenUsuarioUbicacionRepository
                        .findFirstByIdUsuarioAndPredeterminado(mUsuario.getIdUsuario(), Boolean.TRUE);

                LoginResponse entidad = new LoginResponse();
                entidad.setUsuario(new UsuarioResponse(mUsuario, domicilio));
                entidad.setJwt(generarJwt(mUsuario));
                respuesta.setEntidad(entidad);
                respuesta.setEstado(R0002);
            } else {
                respuesta.setEstado(R0001);
            }

        } catch (NegocioException ex) {
            respuesta.setEstado(negocioExceptiontoEstadoResponse(ex));
        } catch (Exception ex) {
            respuesta.setEstado(exceptiontoEstadoResponse(ex));
        }
        return respuesta;
    }

	/**
	 * @see LoginService#registrarUsuario(RegistroUsuarioRequest, Long)
	 */
	@Override
	public EstadoResponse registrarUsuario(RegistroUsuarioRequest peticion, Long idPerfil) {

		try {

			String correoConAES = aes.encriptar(peticion.getCorreo().trim());

			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(correoConAES);

			if (Objects.isNull(idUsuario)) {

				// Mapeamos petición a entidad
				GenUsuario mUsuario = new GenUsuario(peticion);

				// Guardamos registro en base de datos
				mUsuarioRepository.save(mUsuario);

				// Asignamos el perfil inicial segun el actor parametrizado.
				EstadoResponse rPerfilUsuario = mPerfilService.asignarPerfilUsuario(mUsuario.getIdUsuario(), idPerfil);
				if (!rPerfilUsuario.isExitoso()) {
					return rPerfilUsuario;
				}

				// Asignamos variables de estado
				return R0003;
			} else {
				return R00A3;
			}

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}

    /**
     * @see LoginService#enviarCorreoRecuperacionContrasena(String)
     */
    @Override
    public EstadoResponse enviarCorreoRecuperacionContrasena(String correo) {

        try {

            //Consultamos usuario según su correo electrónico.
            GenUsuario mUsuario = mUsuarioRepository.findFirstUsuarioByCorreo(AES.encriptar(correo.trim()));

            //Validamos si existe un usuario con el correo parametrizado.
            if (mUsuario != null) {
            	
            	//Primero inhabilitamos los tokens que están en proceso para el usuario.
            	List<GenRecupContrasena> ultimosIntentos = mRecupContrasenaRepository
						.findByGenRecupContrasenaPKIdUsuarioAndEstadoCambio(mUsuario.getIdUsuario(),
								GenRecupContrasena.ESTADO_TOKEN_PROCESO);
            	
            	if(!CollectionUtils.isEmpty(ultimosIntentos)) {
            		for (GenRecupContrasena genRecupContrasena : ultimosIntentos) {
            			genRecupContrasena.setEstadoCambio(GenRecupContrasena.ESTADO_TOKEN_INHABILITADO);
						mRecupContrasenaRepository.save(genRecupContrasena);
					}
            	}

                // Creamos token y vencimiento
                String token = UUID.randomUUID().toString();
                Date vencimiento = DateUtils.addMinutes(new Date(), 30);
                
                //Creamos contraseña:
                RandomString gen = new RandomString(8, ThreadLocalRandom.current());
                String contrasena = gen.nextString();


                //Salvamos el modelo de reenvio de contraseña
                GenRecupContrasenaPK mRecupContrasenaId = new GenRecupContrasenaPK();
                mRecupContrasenaId.setIdUsuario(mUsuario.getIdUsuario());
                mRecupContrasenaId.setToken(token);

                GenRecupContrasena mRecupContrasena = new GenRecupContrasena();
                mRecupContrasena.setGenRecupContrasenaPK(mRecupContrasenaId);
                mRecupContrasena.setExpiracion(vencimiento);
                mRecupContrasena.setContrasenaNueva(contrasena);
                mRecupContrasena.setEstadoCambio(GenRecupContrasena.ESTADO_TOKEN_PROCESO);

                //Salvamos
                mRecupContrasenaRepository.save(mRecupContrasena);

                //String obtenemos tokenUrl

                //Creamos plantilla para envio de correo
                CorreoPlantillaRequest plantilla =
                        new CorreoPlantillaRequest(AS_RECUP_CONTRASENA, PL_ENVIAR_CONTRASENA);
                plantilla.addPara(correo);
                plantilla.addVariable("vencimiento", "30");
                plantilla.addVariable("password", contrasena);

                //Enviamos correo electrónico
                mEmailService.enviarCorreoPlantilla(plantilla);

                //Asignamos variables de estado
                return R0005;

            } else {
                return R0004;
            }

        } catch (NegocioException ex) {
            return negocioExceptiontoEstadoResponse(ex);
        } catch (Exception ex) {
            return exceptiontoEstadoResponse(ex);
        }
    }

	private GenUsuario obtenerUsuario(String correo, String contrasena) {
		// Consultamos el usuario (correo) y contraseña con SHA512 en la base de datos.
		GenUsuario mUsuario = mUsuarioRepository.findFirstByCorreoAndContrasena(correo, contrasena);

		// Consultamos si el usuario está en proceso de cambio de contraseña.
		if (mUsuario == null) {
			mUsuario = mUsuarioRepository.findFirstUsuarioByCorreo(correo);
			if (mUsuario != null) {
				GenRecupContrasena mRecuperacion = mRecupContrasenaRepository
						.findFirstByGenRecupContrasenaPKIdUsuarioAndEstadoCambio(mUsuario.getIdUsuario(),
								GenRecupContrasena.ESTADO_TOKEN_PROCESO);
				
				//Si el token está en proceso y la fecha de expiración ya venció (Esto ocurre cuando envían, 
				//muchos correos de recuperación y solo usan 1).
				if (mRecuperacion != null) {
					EstadoResponse estadoRecuperacion = validarTokenRecuperarContrasena(mRecuperacion, contrasena);
					if (estadoRecuperacion.isExitoso()) {
						mRecuperacion.setEstadoCambio(GenRecupContrasena.ESTADO_TOKEN_TERMINADO);
						mRecupContrasenaRepository.save(mRecuperacion);
					} else {
						throw new NegocioException(estadoRecuperacion.getMensaje());
					}
				} else {
					mUsuario = null;
				}

			}
		}

		if(mUsuario != null && GenUsuario.ESTADO_INHABILITADO.equals(mUsuario.getEstado())) {
			throw new NegocioException(R00A1.getMensaje());
		}
		
		return mUsuario;
	}

    /**
     * Función de negocio encargada de generar un token de autenticación (JWT)
     *
     * @param mUsuario usuario a relacionar en el token.
     * @return Un token JWT generado desde el Builder.
     */
    private String generarJwt(GenUsuario mUsuario) {

        //1. Obtenemos los ids de los perfiles asignados al usuario.
        List<GenPerfil> genPerfil = mUsuario.getPerfiles();
        List<Long> roles = genPerfil.stream().map(GenPerfil::getIdPerfil).collect(Collectors.toList());

        //2. Parametrizamos los roles relacionados al usuario.
        String authorityString = roles.stream().map(String::valueOf).collect(Collectors.joining(","));
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);

        //3. Construimos el JWT.
        String token = Jwts.builder().setId(SecurityConfig.JWT_KEY)
                .setSubject(mUsuario.getCorreo())
                .claim("authorities",
                        grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 36000000L))
                .signWith(SignatureAlgorithm.HS512, SecurityConfig.JWT_KEY.getBytes()).compact();

        //4. Retornamos el jwt con el prefijo Bearer.
        return "Bearer " + token;
    }

	/**
	 * Función de negocio encargada de validar si un token de cambio de contraseña
	 * es válido.
	 *
	 * @param token token generado para el cambio de contraseña de un usuario.
	 * @return Un estado de respuesta con la validación del token.
	 */
	private EstadoResponse validarTokenRecuperarContrasena(GenRecupContrasena mGenRecupContrasena, String contrasena) {
		try {

			String contrasenaConHash = DigestUtils.sha512Hex(mGenRecupContrasena.getContrasenaNueva());

			// Si el registro es diferente de nulo validamos el vencimiento.
			if(GenRecupContrasena.ESTADO_TOKEN_PROCESO.equals(mGenRecupContrasena.getEstadoCambio())) {
				if (contrasena.equals(contrasenaConHash)) {
					if (mGenRecupContrasena.getExpiracion().before(new Date())) {
						return R0007;
					}
					return R0009;
				} else {
					return R0001;
				}
			}
			else {
				return R0008;
			}

		} catch (NegocioException ex) {
			return negocioExceptiontoEstadoResponse(ex);
		} catch (Exception ex) {
			return exceptiontoEstadoResponse(ex);
		}
	}

}
