package org.sharom.notificationservice.service.impl;

import org.sharom.notificationservice.dto.CreateNotificationRequest;
import org.sharom.notificationservice.entity.Notification;
import org.sharom.notificationservice.entity.NotificationChanel;
import org.sharom.notificationservice.service.NotificationSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender implements NotificationSender {
    @Override
    public NotificationChanel getChanel() {
        return NotificationChanel.EMAIL;
    }

    @Override
    public void send(Notification notification, CreateNotificationRequest req) {

    }
}
