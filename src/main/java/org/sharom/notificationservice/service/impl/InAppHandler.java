package org.sharom.notificationservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.sharom.notificationservice.dto.CreateNotificationRequest;
import org.sharom.notificationservice.dto.InAppRequest;
import org.sharom.notificationservice.entity.*;
import org.sharom.notificationservice.repository.ClientNotificationRepository;
import org.sharom.notificationservice.repository.NotificationRepository;
import org.sharom.notificationservice.service.NotificationHandler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class InAppHandler implements NotificationHandler<InAppRequest> {

    private final NotificationRepository notificationRepository;
    private final ClientNotificationRepository clientNotificationRepository;


    @Override
    @Transactional
    public void handle(InAppRequest request) {

        Notification notification = Notification.builder()
                .receiverType(request.receiverType())
                .readCount(0L)
                .contents(new ArrayList<>())
                .build();

        request.contents().forEach((lang, dto) -> {
            Content content = Content.builder()
                    .lang(lang)
                    .title(dto.title())
                    .body(dto.body())
                    .build();

            notification.addContent(content);
        });

        notificationRepository.save(notification);

        if (request.receiverType() == ReceiverType.DIRECT) {
            sendToDirect(notification, request);
        }

        if (request.sendPush()) {
            sendPush(notification, request);
        }
    }

    @Override
    public Class<InAppRequest> getType() {
        return InAppRequest.class;
    }

    private void sendToDirect(Notification notification, CreateNotificationRequest req) {

        if (CollectionUtils.isEmpty(req.userIds()) || req.receiverType() != ReceiverType.DIRECT) {
            return;
        }

        handleDirect(notification, req);

    }


    private void handleDirect(Notification notification, CreateNotificationRequest req) {
        Instant now = Instant.now();

        List<ClientNotification> notifications =
                req.userIds().stream()
                        .map(uuid -> ClientNotification.builder()
                                .notification(notification)
                                .status(Status.SENT)
                                .userId(uuid)
                                .read(false)
                                .sentAt(now)
                                .build())
                        .collect(Collectors.toList());

        clientNotificationRepository.saveAll(notifications);
    }

}
