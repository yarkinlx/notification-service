package org.example.notificationservice.controller;

import org.example.notificationservice.controller.dto.ApiResponse;
import org.example.notificationservice.controller.dto.NotificationRequest;
import org.example.notificationservice.controller.dto.UserNotificationRequest;
import org.example.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
@Validated
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendNotification(@Valid @RequestBody NotificationRequest request) {
        log.info("Отправка кастомного уведомления на: {}", request.getEmail());
        notificationService.sendCustomNotification(request.getEmail(), request.getSubject(), request.getMessage());

        return ResponseEntity.ok(
                new ApiResponse("Уведомление успешно отправлено на: " + request.getEmail())
        );
    }

    @PostMapping("/user-created")
    public ResponseEntity<ApiResponse> simulateUserCreated(@Valid @RequestBody UserNotificationRequest request) {
        log.info("Симуляция создания пользователя для: {}", request.getEmail());
        notificationService.sendUserCreatedNotification(request.getEmail(), request.getUsername());

        return ResponseEntity.ok(
                new ApiResponse("Уведомление о создании пользователя успешно отправлено")
        );
    }

    @PostMapping("/user-deleted")
    public ResponseEntity<ApiResponse> simulateUserDeleted(@Valid @RequestBody UserNotificationRequest request) {
        log.info("Симуляция удаления пользователя для: {}", request.getEmail());
        notificationService.sendUserDeletedNotification(request.getEmail(), request.getUsername());

        return ResponseEntity.ok(
                new ApiResponse("Уведомление об удалении пользователя успешно отправлено")
        );
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse> healthCheck() {
        return ResponseEntity.ok(new ApiResponse("Notification service is running"));
    }
}