package org.example.notificationservice.service;

import org.example.notificationservice.exception.EmailSendingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final String fromEmail = "Yark1nS@yandex.ru";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            logger.info("🚀 ATTEMPTING TO SEND EMAIL - To: {}, Subject: {}", to, subject);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            message.setFrom(fromEmail);

            mailSender.send(message);
            logger.info("✅ EMAIL SENT SUCCESSFULLY to: {}", to);

        } catch (MailAuthenticationException exception) {
            logger.error("Authentication failed for email: {}", to, exception);
            throw new EmailSendingException("Email authentication failed. Check username and password.", exception);
        } catch (MailSendException exception) {
            logger.error("Failed to send email to: {}", to, exception);
            throw new EmailSendingException("Failed to send email. Check SMTP configuration.", exception);
        } catch (MailException exception) {
            logger.error("Unexpected mail error for email: {}", to, exception);
            throw new EmailSendingException("Unexpected email error: " + exception.getMessage(), exception);
        }
    }

    public void sendUserCreatedEmail(String email) {

        String subject = "Добро пожаловать на наш сайт!";
        String message = "Здравствуйте! Рады сообщить, что ваша регистрация прошла успешно. Добро пожаловать в наше сообщество!";
        sendEmail(email, subject, message);
    }

    public void sendUserDeletedEmail(String email) {

        String subject = "Информация о вашем аккаунте";
        String message = "Здравствуйте! Информируем вас о завершении работы с вашим аккаунтом. Спасибо, что были с нами!";
        sendEmail(email, subject, message);
    }

    private SimpleMailMessage createEmailMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(fromEmail);
        return message;
    }
}