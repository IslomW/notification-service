package org.sharom.notificationservice.exception;

import org.sharom.notificationservice.constant.MessageKey;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{

    private static final String LOG_NAME = "NotFoundException";

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public static NotFoundException notificationNotFound(){
        return new NotFoundException(MessageKey.NOTIFICATION_NOT_FOUND);
    }

    public static NotFoundException tokenNotFound(){
        return new NotFoundException(MessageKey.TOKEN_NOT_FOUND);
    }


    @Override
    public String getLogMessage() {
        return LOG_NAME;
    }
}
