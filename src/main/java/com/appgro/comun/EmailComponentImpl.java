package com.appgro.comun;

import com.appgro.exception.NegocioException;
import com.appgro.model.request.CorreoPlantillaRequest;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class EmailComponentImpl implements EmailComponent {

    /**
     * Servicio Java para envio de correos
     */
    private final JavaMailSender mailSender;

    /**
     * Servicio Thymeleaf para plantillas HTML de correos.
     */
    private final SpringTemplateEngine templateEngine;

    /**
     * Constructor
     * @param mailSender servicio de correo para java
     * @param templateEngine servicio para plantillas de correo.
     */
    public EmailComponentImpl(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * @see EmailComponent#enviarCorreo(SimpleMailMessage)
     */
    @Async
    @Override
    public void enviarCorreo(SimpleMailMessage correoSimple) {
        mailSender.send(correoSimple);
    }

    /**
     * @see EmailComponent#enviarCorreoPlantilla(CorreoPlantillaRequest)
     */
    @Async
    @Override
    public void enviarCorreoPlantilla(CorreoPlantillaRequest correoPlantilla) {

        try {

            //Creamos mensaje de correo.
            MimeMessage mMessage = mailSender.createMimeMessage();
            MimeMessageHelper mHelper = new MimeMessageHelper(mMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            //Configuramos variables a reemplazar en la plantilla
            Context context = new Context();
            context.setVariables(correoPlantilla.getModelo());

            //Construimos HTML con la plantilla y las variables.
            String html = templateEngine.process(correoPlantilla.getPlantilla(), context);

            //Validamos que existan correo para enviar en (Para)
            if(!correoPlantilla.getPara().isEmpty()){
                String[] to = new String[correoPlantilla.getPara().size()];
                to = correoPlantilla.getPara().toArray(to);
                mHelper.setTo(to);
            }

            //Validamos que existan correo para enviar en (Copia)
            if(!correoPlantilla.getCopia().isEmpty()){
                String[] copy = new String[correoPlantilla.getCopia().size()];
                copy = correoPlantilla.getCopia().toArray(copy);
                mHelper.setCc(copy);
            }

            //Validamos que existan correo para enviar en (Copia Oculta)
            if(!correoPlantilla.getCopiaOculta().isEmpty()){
                String[] copyOculta = new String[correoPlantilla.getCopiaOculta().size()];
                copyOculta = correoPlantilla.getCopiaOculta().toArray(copyOculta);
                mHelper.setBcc(copyOculta);
            }

            //Configuramos asunto y texto del correo.
            mHelper.setSubject(correoPlantilla.getAsunto());
            mHelper.setText(html, true);
            
            //Si tenemos imagenes en el template:
			for (Map.Entry<String, InputStreamSource> inline : correoPlantilla.getInLine().entrySet()) {
			    mHelper.addInline(inline.getKey(), inline.getValue(), "image/png");
			}
			       
            mailSender.send(mMessage);

        } catch (Exception ex) {
            throw new NegocioException("Error no se ha podido enviar el correo electrónico", ex);
        }
    }
}
