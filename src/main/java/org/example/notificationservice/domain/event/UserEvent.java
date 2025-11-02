package org.example.notificationservice.domain.event;

import java.time.LocalDateTime;

public class UserEvent {
    private String operation;
    private String email;
    private String username;
    private LocalDateTime timestamp;


    public UserEvent() {
        this.timestamp = LocalDateTime.now();
    }

    public UserEvent(String operation, String email, String username) {
        this.operation = operation;
        this.email = email;
        this.username = username;
        this.timestamp = LocalDateTime.now();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "operation='" + operation + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}