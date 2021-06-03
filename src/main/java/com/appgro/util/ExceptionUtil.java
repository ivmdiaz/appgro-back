package com.appgro.util;

import com.appgro.exception.NegocioException;
import com.appgro.model.response.EstadoResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.springframework.util.CollectionUtils;

public class ExceptionUtil {

    private ExceptionUtil(){
        throw new UnsupportedOperationException();
    }

    /* CONSTANTES */
    private static final String EXCEPCION_NEGOCIO = "Proceso con errores, verifique los mensajes de error para más información.";
    private static final String EXCEPCION_SISTEMA = "Error general del sistema. Por favor intente más tarde.";

    /**
     * Función utilitaria para conseguir un EstadoResponse a partir de una excepción de negocio
     *
     * @param ex execepción de negocio capturada
     * @return retorna un Estado general del sistema
     */
    public static EstadoResponse negocioExceptiontoEstadoResponse(NegocioException ex) {
        EstadoResponse respuesta = new EstadoResponse();
        try {
            respuesta.setExitoso(Boolean.FALSE);
            respuesta.setErrores(ex.getMensajes());
            respuesta.setEstampaTiempo(new Date());
            respuesta.setMensaje(ex.getMessage());
        } catch (Exception ex2) {
            respuesta = exceptiontoEstadoResponse(ex2);
        }
        return respuesta;
    }

    /**
     * Función utilitaria para conseguir un EstadoResponse a partir de una excepción del sistema
     *
     * @param ex execepción del sistema capturada
     * @return retorna un Estado general del sistema
     */
    public static EstadoResponse exceptiontoEstadoResponse(Exception ex) {
        EstadoResponse respuesta = new EstadoResponse();
        try {
            respuesta.setExitoso(Boolean.FALSE);
            respuesta.setEstampaTiempo(new Date());
            respuesta.setMensaje(EXCEPCION_SISTEMA);

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString();
            respuesta.getErrores().add(sStackTrace);

            ex.printStackTrace();

        } catch (Exception ex2) {
            respuesta.setExitoso(Boolean.FALSE);
            respuesta.setEstampaTiempo(new Date());
            respuesta.setMensaje(EXCEPCION_SISTEMA);
        }
        return respuesta;
    }
}
