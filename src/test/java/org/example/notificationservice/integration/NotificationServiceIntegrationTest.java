package org.example.notificationservice.integration;

import org.example.notificationservice.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.mail.host=localhost",
        "spring.mail.port=3025",
        "spring.mail.from=test@example.com"
})
class NotificationServiceIntegrationTest {

    @Autowired
    private NotificationService notificationService;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    void sendUserCreatedNotification_Integration_ShouldSendEmail() {

        String email = "integration@example.com";
        String username = "Integration User";


        notificationService.sendUserCreatedNotification(email, username);


        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendUserDeletedNotification_Integration_ShouldSendEmail() {

        String email = "integration@example.com";
        String username = "Integration User";


        notificationService.sendUserDeletedNotification(email, username);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendCustomNotification_Integration_ShouldSendEmail() {

        String email = "integration@example.com";
        String subject = "Test Subject";
        String message = "Test Message";

        notificationService.sendCustomNotification(email, subject, message);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}