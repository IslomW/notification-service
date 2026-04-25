package org.sharom.notificationservice.service.impl;


import org.sharom.notificationservice.dto.NotificationRequest;
import org.sharom.notificationservice.service.NotificationHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HandlerRegistry {

    private final Map<Class<?>, NotificationHandler<?>> handlers = new HashMap<>();

    public HandlerRegistry(List<NotificationHandler<?>> handlerList) {
        for (NotificationHandler<?> handler : handlerList) {
            handlers.put(handler.getType(), handler);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends NotificationRequest> NotificationHandler<T> get(T request) {

        NotificationHandler<?> handler = handlers.get(request.getClass());

        if (handler == null) {
            throw new IllegalArgumentException(
                    "No handler found for " + request.getClass()
            );
        }

        return (NotificationHandler<T>) handler;
    }

}
