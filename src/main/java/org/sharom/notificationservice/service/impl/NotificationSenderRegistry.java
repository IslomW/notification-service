package org.sharom.notificationservice.service.impl;

import org.sharom.notificationservice.entity.NotificationChanel;
import org.sharom.notificationservice.service.NotificationSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationSenderRegistry {

    private final Map<NotificationChanel, NotificationSender> senderMap;


    public NotificationSenderRegistry(List<NotificationSender> senders) {
        this.senderMap = senders.stream()
                .collect(Collectors.toMap(NotificationSender::getChanel, Function.identity()));
    }


    public NotificationSender get(NotificationChanel chanel) {
        return senderMap.get(chanel);
    }
}
