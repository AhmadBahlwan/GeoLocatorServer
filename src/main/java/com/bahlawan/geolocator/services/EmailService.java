package com.bahlawan.geolocator.services;

import com.bahlawan.geolocator.entities.GeoLocation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${email.from}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String toEmail, List<GeoLocation> geoLocationDetails) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail, "Geo Locator App");
        helper.setTo(toEmail);
        helper.setSubject("Geo Location Details");

        StringBuilder content = new StringBuilder();

        for (GeoLocation geoLocationItem : geoLocationDetails) {
            content.append("Address: ").append(geoLocationItem.getAddress()).append("<br>")
                    .append("Latitude: ").append(geoLocationItem.getLatitude()).append("<br>")
                    .append("Longitude: ").append(geoLocationItem.getLongitude()).append("<br>")
                    .append("<br>");
        }

        helper.setText(content.toString(), true);
        mailSender.send(message);
    }
}
