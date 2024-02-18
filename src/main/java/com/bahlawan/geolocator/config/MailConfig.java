package com.bahlawan.geolocator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${EMAIL_HOST}")
    private String host;

    @Value("${EMAIL_PORT}")
    private int port;

    @Value("${EMAIL_USERNAME}")
    private String username;

    @Value("${EMAIL_PASSWORD}")
    private String password;

    @Value("${smtp.auth}")
    private String smtpAuth;

    @Value("${smtp.starttls}")
    private String smtpStarttls;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties mailProperties = new Properties();
        mailProperties.setProperty(smtpAuth, "true");
        mailProperties.setProperty(smtpStarttls, "true");
        mailSender.setJavaMailProperties(mailProperties);
        return mailSender;
    }
}
