package com.example.login.service;
import com.example.login.exception.EmailServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
@Service
@Slf4j
public class EmailService {


    @Value("${sender.email.host}")
    private String emailHost;

    @Value("${sender.email.port}")
    private String emailPort;

    @Value("${sender.email.id}")
    private String senderEmailId;

    @Value("${sender.email.password}")
    private String senderPassword;

    private static final String EMAIL_CONTENT_TYPE = "text/html";

    private static final String EMAIL_FORGOT_PASSWORD_SUBJECT = "login Change Password";

    private static final String EMAIL_FORGOT_PASSWORD_MESSAGE =
            "<div>%s is the OTP for Change Password of login Account.OTP is valid for %s minutes.Please do not share it with anyone.</div>";


    public void sendForgotPasswordEmail(String userEmail, String otp, Long otpExpiryTime) throws EmailServiceException {

        try {
            Message message = getEmailInitialConfiguration();
            InternetAddress[] toAddresses = {new InternetAddress(userEmail)};
            message.setRecipients(Message.RecipientType.TO, toAddresses);
            message.setSubject(EMAIL_FORGOT_PASSWORD_SUBJECT);
            message.setContent(
                    String.format(EMAIL_FORGOT_PASSWORD_MESSAGE, otp, otpExpiryTime), EMAIL_CONTENT_TYPE);
            Transport.send(message);
        } catch (Exception e) {
            log.error(
                    "Error while sending otp to user email from email service,userEmail :{},Error :",
                    userEmail,
                    e);
            throw new EmailServiceException(userEmail);
        }
    }
    private Message getEmailInitialConfiguration() throws MessagingException {

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", emailHost);
        properties.put("mail.smtp.port", emailPort);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");

        Authenticator auth =
                new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmailId, senderPassword);
                    }
                };
        Session session = Session.getInstance(properties, auth);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmailId));
        message.setSentDate(new Date());
        return message;
    }

}
