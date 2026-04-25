package org.sharom.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sharom.notificationservice.dto.NotificationDTO;
import org.sharom.notificationservice.entity.ClientNotification;
import org.sharom.notificationservice.entity.Lang;
import org.sharom.notificationservice.entity.Notification;
import org.sharom.notificationservice.entity.Status;
import org.sharom.notificationservice.exception.NotFoundException;
import org.sharom.notificationservice.repository.ClientNotificationRepository;
import org.sharom.notificationservice.repository.NotificationRepository;
import org.sharom.notificationservice.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final ClientNotificationRepository clientNotificationRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public Page<NotificationDTO> getAllUserNotifications(Pageable pageable) {
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        return notificationRepository.findAllForUser(userId, Lang.RU, pageable);
    }

    @Override
    public NotificationDTO getNotificationById(UUID notificationId) {
        // getLang

        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        clientNotificationRepository
                .findOrCreateAndMarkRead(userId, notificationId);

        return clientNotificationRepository
                .findNotificationDtoByNoteId(notificationId, Lang.RU)
                .orElseThrow(NotFoundException::notificationNotFound);

    }

    @Override
    public void markAsReadAllNotifications() {
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        clientNotificationRepository.markAllAsRead(userId);

        // 2.
        List<Notification> missingNotifications =
                notificationRepository.findAllGlobalNotLinkedToUser(userId);

        if (!missingNotifications.isEmpty()) {

            Instant now = Instant.now();

            List<ClientNotification> newClientNotifications =
                    missingNotifications.stream()
                            .map(notification -> ClientNotification.builder()
                                    .userId(userId)
                                    .notification(notification)
                                    .read(true)
                                    .status(Status.SENT)
                                    .sentAt(notification.getCreatedAt())
                                    .readAt(now)
                                    .build())
                            .collect(Collectors.toList());

            clientNotificationRepository.saveAll(newClientNotifications);
        }

    }

    @Override
    public Long getUnreadCount() {
        // Get userId(token)
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        return notificationRepository.countTotalUnread(userId);
    }


}
