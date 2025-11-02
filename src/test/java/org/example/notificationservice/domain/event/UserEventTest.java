package org.example.notificationservice.domain.event;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserEventTest {

    @Test
    void userEvent_ShouldWorkCorrectly() {

        UserEvent event = new UserEvent("create", "test@example.com", "Test User");


        assertEquals("create", event.getOperation());
        assertEquals("test@example.com", event.getEmail());
        assertEquals("Test User", event.getUsername());
        assertNotNull(event.getTimestamp());
        assertTrue(event.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void userEvent_DefaultConstructor_ShouldSetTimestamp() {

        UserEvent event = new UserEvent();
        event.setOperation("delete");
        event.setEmail("test@example.com");
        event.setUsername("Test User");


        assertEquals("delete", event.getOperation());
        assertEquals("test@example.com", event.getEmail());
        assertEquals("Test User", event.getUsername());
        assertNotNull(event.getTimestamp());
    }

    @Test
    void userEvent_ToString_ShouldContainAllFields() {

        UserEvent event = new UserEvent("create", "test@example.com", "Test User");


        String toString = event.toString();


        assertTrue(toString.contains("create"));
        assertTrue(toString.contains("test@example.com"));
        assertTrue(toString.contains("Test User"));
        assertTrue(toString.contains("timestamp"));
    }
}