package org.sharom.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.sharom.notificationservice.dto.CreateNotificationRequest;
import org.sharom.notificationservice.entity.DeviceToken;
import org.sharom.notificationservice.entity.Notification;
import org.sharom.notificationservice.entity.NotificationChanel;
import org.sharom.notificationservice.repository.DeviceTokenRepository;
import org.sharom.notificationservice.service.NotificationSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PushSender implements NotificationSender {

    private final DeviceTokenRepository deviceTokenRepository;

    @Override
    public NotificationChanel getChanel() {
        return NotificationChanel.PUSH;
    }

    @Override
    public void send(Notification notification, CreateNotificationRequest req) {


        List<DeviceToken> tokens = deviceTokenRepository
                .findByUserIdIn(req.userIds());


        if (tokens.isEmpty()){
            return;
        }


    }
}
