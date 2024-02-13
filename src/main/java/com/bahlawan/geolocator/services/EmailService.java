package com.bahlawan.geolocator.services;

import com.bahlawan.geolocator.entities.GeoLocation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {
    public void sendMail(List<GeoLocation> geoLocationDetails) throws MessagingException, UnsupportedEncodingException {
        JavaMailSenderImpl mailSender =
                prepareMailSender
                        ();
        String toAddress =
                "ahmad.abdalziz.bhlwan@gmail.com"
                ;
        String subject =
                "test"
                ;
//        String content =
//                "Hello from shrebati"
//                ;
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(
//                "alfateh1997@hotmail.com"
//        );
//        message.setTo(toAddress);
//        message.setSubject(subject);
//        message.setText(content);
//        mailSender.send(message);

        StringBuilder content = new StringBuilder("<p>Hello,</p>"
                + "<p>You have requested to get your address geo location</p>"
                + "<p>Details below:</p>");


        for (GeoLocation geoLocationItem: geoLocationDetails) {
            content.append("address: ").append(geoLocationItem.getAddress()).append("<br>")
                    .append("country: ").append(geoLocationItem.getCountry()).append("<br>")
                    .append("city: ").append(geoLocationItem.getCity()).append("<br>")
                    .append("postal code: ").append(geoLocationItem.getPostalCode()).append("<br>")
                    .append("latitude : ").append(geoLocationItem.getLatitude()).append("<br>")
                    .append("longitude: ").append(geoLocationItem.getLongitude()).append("<br>")
                    .append(geoLocationItem.getLongitude()).append("<br>");
        }
        content.append("<br><br><br><br>");
        content.append("<b>thanks for using our application</b>");
        content.append("<font color=\"red\"><i>Geo locator team</i></font>");



        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("alfateh1997@hotmail.com", "Geo Locator App");
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content.toString(), true);
        mailSender.send(message);
    }
    public static JavaMailSenderImpl prepareMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(
                "smtp-mail.outlook.com"
        );
        mailSender.setPort(587);
        mailSender.setUsername(
                "alfateh1997@hotmail.com"
        );
        mailSender.setPassword(
                "Bahlawan1997"
        );
        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", "true");
        mailProperties.setProperty("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(mailProperties);
        return mailSender;
    }
}
