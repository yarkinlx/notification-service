package org.example.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.config.EmailProperties;
import org.example.notificationservice.exception.EmailSendingException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    public void sendUserCreatedNotification(String email, String username) {
        String subject = "Добро пожаловать!";
        String message = String.format(
                "Здравствуйте, %s! Ваш аккаунт на сайте был успешно создан.",
                username
        );
        sendEmail(email, subject, message);
    }

    public void sendUserDeletedNotification(String email, String username) {
        String subject = "Аккаунт удален";
        String message = String.format(
                "Здравствуйте, %s! Ваш аккаунт был удалён.",
                username
        );
        sendEmail(email, subject, message);
    }

    public void sendCustomNotification(String email, String subject, String message) {
        sendEmail(email, subject, message);
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailProperties.getFrom());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            log.info("Email успешно отправлен на: {}", to);
        } catch (MailException e) {
            log.error("Ошибка отправки email на: {}", to, e);
            throw new EmailSendingException("Не удалось отправить email на: " + to, e);
        }
    }
}