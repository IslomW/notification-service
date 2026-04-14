package org.sharom.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.sharom.notificationservice.dto.ContentDTO;
import org.sharom.notificationservice.dto.CreateNotificationRequest;
import org.sharom.notificationservice.entity.Lang;
import org.sharom.notificationservice.entity.Notification;
import org.sharom.notificationservice.entity.NotificationChanel;
import org.sharom.notificationservice.repository.ContentRepository;
import org.sharom.notificationservice.repository.NotificationRepository;
import org.sharom.notificationservice.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationSenderRegistry senderRegistry;
    private final NotificationRepository notificationRepository;
    private final ContentRepository contentRepository;


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

    private void saveContents(Notification notification, Map<Lang, ContentDTO> contents){

    }
}
