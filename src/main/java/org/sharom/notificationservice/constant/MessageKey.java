package org.sharom.notificationservice.constant;

import lombok.Getter;

@Getter
public class MessageKey {

    private MessageKey() {
    }

    public static final String TOKEN_NOT_FOUND = "token.not.found";
    public static final String NOTIFICATION_NOT_FOUND = "notification.not.found";
}
