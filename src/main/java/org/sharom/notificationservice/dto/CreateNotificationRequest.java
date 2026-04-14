package org.sharom.notificationservice.dto;

import org.sharom.notificationservice.entity.Lang;
import org.sharom.notificationservice.entity.NotificationChanel;
import org.sharom.notificationservice.entity.ReceiverType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record CreateNotificationRequest(
        NotificationChanel chanelType,
        ReceiverType receiverType,
        List<UUID> userIds,
        Map<Lang, ContentDTO> contents,
        boolean sendPush
) {
}
