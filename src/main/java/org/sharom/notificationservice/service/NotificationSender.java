package org.sharom.notificationservice.service;

import org.sharom.notificationservice.dto.CreateNotificationRequest;
import org.sharom.notificationservice.entity.Notification;
import org.sharom.notificationservice.entity.NotificationChanel;

public interface NotificationSender {

    NotificationChanel getChanel();

    void send(Notification notification, CreateNotificationRequest req);
}
