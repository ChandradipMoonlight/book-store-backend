package com.bridgelabz.bookstore.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
@Slf4j
public class MailUtil {

    public void sendEmail(String toEmail,  String subject, String body) throws MessagingException {
        final String fromEmail = "chandradip96shivankar@gmail.com";
        final String password = "ddymkytcfrdhthxn";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        // SMTP Host
        properties.put("mail.smtp.port", "587");
        // TLS Port
        properties.put("mail.smtp.auth", "true");
        // enable authentication
        properties.put("mail.smtp.starttls.enable", "true");
        // enable STARTTLS
        // to check email sender credentials are valid or not
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);

            }
        };
        javax.mail.Session session = Session.getInstance(properties, auth);
        try {
            MimeMessage message = new MimeMessage(session);
            message.addHeader("Content-Type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");
            message.setFrom(new InternetAddress("no_reply@gmail.com", "NoReply"));
            message.setReplyTo(InternetAddress.parse("chandradip96shivankar@gmail.com", false));
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8", "html");
            message.setSentDate(new Date());
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(message);
            System.out.println("Email Sent Successfully.........");
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("sendEmail method is executed successfully.");
    }

}
