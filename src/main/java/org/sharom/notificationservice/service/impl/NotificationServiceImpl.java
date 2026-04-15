package org.sharom.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.sharom.notificationservice.dto.ContentDTO;
import org.sharom.notificationservice.dto.CreateNotificationRequest;
import org.sharom.notificationservice.dto.NotificationDTO;
import org.sharom.notificationservice.entity.ClientNotification;
import org.sharom.notificationservice.entity.Content;
import org.sharom.notificationservice.entity.Lang;
import org.sharom.notificationservice.entity.Notification;
import org.sharom.notificationservice.repository.ClientNotificationRepository;
import org.sharom.notificationservice.repository.ContentRepository;
import org.sharom.notificationservice.repository.NotificationRepository;
import org.sharom.notificationservice.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationSenderRegistry senderRegistry;
    private final NotificationRepository notificationRepository;
    private final ContentRepository contentRepository;
    private final ClientNotificationRepository clientNotificationRepository;


    @Override
    public void createNotification(CreateNotificationRequest request) {
        Notification notification = Notification.builder()
                .notificationChanel(request.chanelType())
                .receiverType(request.receiverType())
                .build();
        notificationRepository.save(notification);

        saveContents(notification, request.contents());

        // send ot chanel
        senderRegistry.get(request.chanelType()).send(notification, request);


    }

    @Override
    public Page<NotificationDTO> getAllUserNotifications(Pageable pageable) {
        return null;
    }

    @Override
    public NotificationDTO getNotificationById(UUID notificationId) {
        return clientNotificationRepository.findNotificationDtoById(notificationId, Lang.RU)
                .orElseThrow(() -> new RuntimeException("notification.not.found"));
    }

    @Override
    public void markAsReadById(UUID notificationId) {
        ClientNotification clientNotification = clientNotificationRepository.getClientNotificationsById(notificationId)
                .orElseThrow(() -> new RuntimeException("notification.not.found"));
        clientNotification.setRead(true);
        clientNotification.setReadAt(Instant.now());
        clientNotificationRepository.save(clientNotification);
    }

    @Override
    public void markAsReadAllNotifications() {

    }

    @Override
    public Long getUnreadCount() {
        // Get userId(token)
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        return notificationRepository.countTotalUnread(userId);
    }

    private void saveContents(Notification notification, Map<Lang, ContentDTO> contents) {
        List<Content> contentList = contents.entrySet()
                .stream()
                .map(entry -> Content.builder()
                        .notification(notification)
                        .lang(entry.getKey())
                        .title(entry.getValue().title())
                        .body(entry.getValue().body())
                        .build()
                )
                .collect(Collectors.toList());

        contentRepository.saveAll(contentList);
    }
}
