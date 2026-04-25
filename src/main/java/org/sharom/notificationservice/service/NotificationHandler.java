package org.sharom.notificationservice.service;

import org.sharom.notificationservice.dto.NotificationRequest;

public interface NotificationHandler <T extends NotificationRequest> {
    void handle(T request);

    Class<T> getType();
}
