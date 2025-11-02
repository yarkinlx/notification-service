package org.example.notificationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.controller.dto.ApiResponse;
import org.example.notificationservice.controller.dto.NotificationRequest;
import org.example.notificationservice.controller.dto.UserNotificationRequest;
import org.example.notificationservice.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@Validated
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

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