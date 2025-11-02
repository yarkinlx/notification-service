package org.example.notificationservice.controller;

import org.example.notificationservice.controller.dto.ApiResponse;
import org.example.notificationservice.controller.dto.NotificationRequest;
import org.example.notificationservice.controller.dto.UserNotificationRequest;
import org.example.notificationservice.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void sendNotification_WithValidRequest_ShouldReturnSuccess() {

        NotificationRequest request = new NotificationRequest();
        request.setEmail("test@example.com");
        request.setSubject("Test Subject");
        request.setMessage("Test Message");

        ResponseEntity<ApiResponse> response = notificationController.sendNotification(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Уведомление успешно отправлено"));
        verify(notificationService).sendCustomNotification("test@example.com", "Test Subject", "Test Message");
    }

    @Test
    void simulateUserCreated_WithValidRequest_ShouldReturnSuccess() {
        UserNotificationRequest request = new UserNotificationRequest();
        request.setEmail("test@example.com");
        request.setUsername("Test User");

        ResponseEntity<ApiResponse> response = notificationController.simulateUserCreated(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Уведомление о создании пользователя"));
        verify(notificationService).sendUserCreatedNotification("test@example.com", "Test User");
    }

    @Test
    void simulateUserDeleted_WithValidRequest_ShouldReturnSuccess() {

        UserNotificationRequest request = new UserNotificationRequest();
        request.setEmail("test@example.com");
        request.setUsername("Test User");

        ResponseEntity<ApiResponse> response = notificationController.simulateUserDeleted(request);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Уведомление об удалении пользователя"));
        verify(notificationService).sendUserDeletedNotification("test@example.com", "Test User");
    }

    @Test
    void healthCheck_ShouldReturnServiceRunning() {

        ResponseEntity<ApiResponse> response = notificationController.healthCheck();


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notification service is running", response.getBody().getMessage());
    }
}