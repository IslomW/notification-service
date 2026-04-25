package org.sharom.notificationservice.service;


import org.sharom.notificationservice.dto.CreateNotificationRequest;
import org.sharom.notificationservice.dto.NotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationService {

    // Mobile
    Page<NotificationDTO> getAllUserNotifications(Pageable pageable);

    NotificationDTO getNotificationById(UUID notificationId);

    void markAsReadAllNotifications();

    Long getUnreadCount();


}
