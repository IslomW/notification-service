package org.sharom.notificationservice.dto;

import java.time.Instant;
import java.util.UUID;

public record NotificationDTO(
        UUID id,
        String title,
        String body,
        boolean isRead,
        Instant sentAt
) {
}
