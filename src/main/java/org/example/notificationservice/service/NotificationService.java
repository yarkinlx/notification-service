package org.example.notificationservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.notificationservice.exception.EmailSendingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    @CircuitBreaker(name = "notificationService", fallbackMethod = "sendCustomNotificationFallback")
    public void sendCustomNotification(String email, String subject, String message) {
        logger.info("Sending custom notification to: {}", email);
        emailService.sendEmail(email, subject, message);
    }

    public void sendCustomNotificationFallback(String email, String subject, String message, Throwable throwable) {
        logger.error("Fallback method called for sendCustomNotification due to: {}", throwable.getMessage());
        throw new EmailSendingException("Notification service is temporarily unavailable");
    }

    @CircuitBreaker(name = "notificationService")
    public void sendUserCreatedNotification(String email, String username) {
        String subject = "Добро пожаловать!";
        String message = String.format("Здравствуйте, %s! Ваш аккаунт на сайте был успешно создан.", username);
        emailService.sendEmail(email, subject, message);
    }

    @CircuitBreaker(name = "notificationService")
    public void sendUserDeletedNotification(String email, String username) {
        String subject = "Аккаунт удален";
        String message = String.format("Здравствуйте, %s! Ваш аккаунт был удалён.", username);
        emailService.sendEmail(email, subject, message);
    }
}
