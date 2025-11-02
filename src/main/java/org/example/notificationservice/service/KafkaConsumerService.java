package org.example.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.domain.event.UserEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final NotificationService notificationService;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void consumeUserEvent(UserEvent event) {
        log.info("Получено событие пользователя: {} для пользователя: {}", event.getOperation(), event.getEmail());

        try {
            switch (event.getOperation().toLowerCase()) {
                case "create":
                    notificationService.sendUserCreatedNotification(event.getEmail(), event.getUsername());
                    log.info("Уведомление о создании отправлено на: {}", event.getEmail());
                    break;
                case "delete":
                    notificationService.sendUserDeletedNotification(event.getEmail(), event.getUsername());
                    log.info("Уведомление об удалении отправлено на: {}", event.getEmail());
                    break;
                default:
                    log.warn("Неизвестная операция: {}", event.getOperation());
            }
        } catch (Exception e) {
            log.error("Ошибка обработки события пользователя для email: {}", event.getEmail(), e);
        }
    }
}