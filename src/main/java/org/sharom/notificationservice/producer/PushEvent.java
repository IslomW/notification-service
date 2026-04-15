package org.sharom.notificationservice.producer;

import java.util.List;
import java.util.UUID;

public record PushEvent(
        UUID eventId,
        UUID notificationId,
        List<String> tokens,
        int retryCount
) {
}
