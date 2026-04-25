package org.sharom.notificationservice.dto;

import org.sharom.notificationservice.entity.Lang;
import org.sharom.notificationservice.entity.Notification;
import org.sharom.notificationservice.entity.ReceiverType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record PushRequest(
        ReceiverType receiverType,
        List<UUID> userIds,
        Notification notification
) implements NotificationRequest {
}
