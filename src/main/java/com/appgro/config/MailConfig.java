package com.appgro.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration 
public class MailConfig {
	
	@Value("${appgro.smtp.email}")
	private String email;
	
	@Value("${appgro.smtp.password}")
	private String password;
	
	@Value("${appgro.smtp.host}")
	private String host;
	
	@Value("${appgro.smtp.port}")
	private Integer port;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(this.host);
        javaMailSender.setPort(this.port);
        javaMailSender.setUsername(this.email);
        javaMailSender.setPassword(this.password);

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");
        return properties;
    }
}