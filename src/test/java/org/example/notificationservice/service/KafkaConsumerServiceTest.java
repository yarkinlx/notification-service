package org.example.notificationservice.service;

import org.example.notificationservice.domain.event.UserEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @Test
    void consumeUserEvent_WithCreateOperation_ShouldSendCreatedNotification() {

        UserEvent event = new UserEvent("create", "test@example.com", "Test User");


        kafkaConsumerService.consumeUserEvent(event);


        verify(notificationService).sendUserCreatedNotification("test@example.com", "Test User");
    }

    @Test
    void consumeUserEvent_WithDeleteOperation_ShouldSendDeletedNotification() {

        UserEvent event = new UserEvent("delete", "test@example.com", "Test User");


        kafkaConsumerService.consumeUserEvent(event);


        verify(notificationService).sendUserDeletedNotification("test@example.com", "Test User");
    }

    @Test
    void consumeUserEvent_WithUnknownOperation_ShouldNotSendAnyNotification() {

        UserEvent event = new UserEvent("unknown", "test@example.com", "Test User");

        kafkaConsumerService.consumeUserEvent(event);

        verify(notificationService, never()).sendUserCreatedNotification(anyString(), anyString());
        verify(notificationService, never()).sendUserDeletedNotification(anyString(), anyString());
    }

    @Test
    void consumeUserEvent_WhenServiceThrowsException_ShouldNotPropagateException() {

        UserEvent event = new UserEvent("create", "test@example.com", "Test User");
        doThrow(new RuntimeException("Service error")).when(notificationService)
                .sendUserCreatedNotification(anyString(), anyString());

        kafkaConsumerService.consumeUserEvent(event);

        verify(notificationService).sendUserCreatedNotification("test@example.com", "Test User");
    }

    @Test
    void consumeUserEvent_WithUpperCaseOperation_ShouldWork() {

        UserEvent event = new UserEvent("CREATE", "test@example.com", "Test User");

        kafkaConsumerService.consumeUserEvent(event);

        verify(notificationService).sendUserCreatedNotification("test@example.com", "Test User");
    }
}