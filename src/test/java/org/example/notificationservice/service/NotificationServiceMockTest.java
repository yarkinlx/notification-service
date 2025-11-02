package org.example.notificationservice.service;

import org.example.notificationservice.config.EmailProperties;
import org.example.notificationservice.exception.EmailSendingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceMockTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailProperties emailProperties;

    @InjectMocks
    private NotificationService notificationService;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @Test
    void sendUserCreatedNotification_ShouldSendEmail() {

        String email = "test@example.com";
        String username = "Test User";
        when(emailProperties.getFrom()).thenReturn("noreply@example.com");


        notificationService.sendUserCreatedNotification(email, username);


        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals(email, sentMessage.getTo()[0]);
        assertEquals("Добро пожаловать!", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("аккаунт на сайте был успешно создан"));
        assertEquals("noreply@example.com", sentMessage.getFrom());
    }

    @Test
    void sendUserDeletedNotification_ShouldSendEmail() {

        String email = "test@example.com";
        String username = "Test User";
        when(emailProperties.getFrom()).thenReturn("noreply@example.com");


        notificationService.sendUserDeletedNotification(email, username);


        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals(email, sentMessage.getTo()[0]);
        assertEquals("Аккаунт удален", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("аккаунт был удалён"));
    }

    @Test
    void sendCustomNotification_ShouldSendEmail() {

        String email = "test@example.com";
        String subject = "Test Subject";
        String message = "Test Message";
        when(emailProperties.getFrom()).thenReturn("noreply@example.com");


        notificationService.sendCustomNotification(email, subject, message);


        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals(email, sentMessage.getTo()[0]);
        assertEquals(subject, sentMessage.getSubject());
        assertEquals(message, sentMessage.getText());
    }

    @Test
    void sendEmail_WhenMailException_ShouldThrowEmailSendingException() {
        String email = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Message";
        when(emailProperties.getFrom()).thenReturn("noreply@example.com");
        doThrow(new MailAuthenticationException("Auth failed")).when(mailSender).send(any(SimpleMailMessage.class));

        assertThrows(EmailSendingException.class,
                () -> notificationService.sendCustomNotification(email, subject, text));
    }
}