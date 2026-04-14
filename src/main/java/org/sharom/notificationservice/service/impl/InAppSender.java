package org.sharom.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.sharom.notificationservice.dto.CreateNotificationRequest;
import org.sharom.notificationservice.entity.*;
import org.sharom.notificationservice.repository.ClientNotificationRepository;
import org.sharom.notificationservice.service.NotificationSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InAppSender implements NotificationSender {

    private final ClientNotificationRepository clientNotificationRepository;

    @Override
    public NotificationChanel getChanel() {
        return NotificationChanel.IN_APP;
    }

    @Override
    public void send(Notification notification, CreateNotificationRequest req) {

        if (req.receiverType() == ReceiverType.DIRECT) {
            List<ClientNotification> clientNotificationList =
                    req.userIds().stream()
                            .map(uuid -> ClientNotification.builder()
                                    .notification(notification)
                                    .status(Status.SENT)
                                    .userId(uuid)
                                    .isRead(false)
                                    .sentAt(Instant.now())
                                    .build())
                            .collect(Collectors.toList());

            clientNotificationRepository.saveAll(clientNotificationList);
        }

    }
}
