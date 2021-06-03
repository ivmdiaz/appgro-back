package com.appgro.comun;

import com.appgro.model.request.CorreoPlantillaRequest;
import org.springframework.mail.SimpleMailMessage;

public interface EmailComponent {

    /**
     * Enviar correo electrónico simple
     * @param correoSimple correo spring boot simple
     */
    void enviarCorreo(SimpleMailMessage correoSimple);

    /**
     * Enviar correo electronico con una plantilla
     * @param correoPlantilla objeto para enviar correo personalizado
     */
    void enviarCorreoPlantilla(CorreoPlantillaRequest correoPlantilla);

}
