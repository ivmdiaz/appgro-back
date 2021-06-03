package com.appgro.exception;

import java.util.ArrayList;
import java.util.List;

public class NegocioException extends RuntimeException {

    private List<String> mensajes;

    /**
     * @param message mensaje de error
     */
    public NegocioException(String message) {
        super(message);
        mensajes = new ArrayList<>();
    }

    /**
     * @param message
     * @param cause
     */
    public NegocioException(String message, Exception cause) {
        super(message, cause);
        mensajes = new ArrayList<>();
    }

    /**
     * @param cause
     */
    public NegocioException(Exception cause) {
        super(cause);
    }

    /**
     * @return the mensajes
     */
    public List<String> getMensajes() {
        return mensajes;
    }

    /**
     * @param mensajes the mensajes to set
     */
    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }

    /**
     * Permite adicionar errores a la lista de mensajes
     *
     * @param error
     */
    public void addError(String error) {
        mensajes.add(error);
    }

    /**
     * Indica si tiene errores la lista
     *
     * @return
     */
    public boolean hasErrors() {
        return !mensajes.isEmpty();
    }

    /**
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
        if (this.hasErrors()) {
            StringBuilder mensajeFinal = new StringBuilder();
            for (int i = 0; i < mensajes.size(); i++) {
                mensajeFinal.append(String.format("%d. %s", (i + 1), mensajes.get(i)));
            }
            return mensajeFinal.toString();
        } else {
            return super.getMessage();
        }
    }

    public String getErrorMessage() {
        return super.getMessage();
    }

}
