package org.example.notificationservice.controller.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void notificationRequest_ShouldWorkCorrectly() {

        NotificationRequest request = new NotificationRequest("test@example.com", "Test Subject", "Test Message");


        assertEquals("test@example.com", request.getEmail());
        assertEquals("Test Subject", request.getSubject());
        assertEquals("Test Message", request.getMessage());
    }

    @Test
    void userNotificationRequest_ShouldWorkCorrectly() {

        UserNotificationRequest request = new UserNotificationRequest("test@example.com", "Test User");

        assertEquals("test@example.com", request.getEmail());
        assertEquals("Test User", request.getUsername());
    }

    @Test
    void apiResponse_ShouldWorkCorrectly() {

        ApiResponse response = new ApiResponse("Test message");

        assertEquals("Test message", response.getMessage());
        assertNotNull(response.getTimestamp());
        assertTrue(response.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void errorResponse_ShouldWorkCorrectly() {

        ErrorResponse response = new ErrorResponse("TEST_ERROR", "Test error message");

        assertEquals("TEST_ERROR", response.getError());
        assertEquals("Test error message", response.getMessage());
        assertNotNull(response.getTimestamp());
        assertTrue(response.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
}