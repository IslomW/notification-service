package org.sharom.notificationservice.service;


import org.sharom.notificationservice.dto.CreateNotificationRequest;

public interface NotificationService {

    void createNotification(CreateNotificationRequest request);
}
