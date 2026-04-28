package org.sharom.notificationservice.service.impl;

import org.sharom.notificationservice.contex.LangContext;
import org.sharom.notificationservice.service.MessageService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    public MessageServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String get(String key) {
        return messageSource.getMessage(
                key,
                null,
                key,
                resolveLocale()
        );
    }

    private Locale resolveLocale() {
        String lang = LangContext.getLang();
        if (lang == null) {
            return Locale.ENGLISH;
        }
        return switch (lang) {
            case "ru" -> new Locale("ru");
            case "uz" -> new Locale("uz");
            default -> new Locale("en");
        };
    }
}