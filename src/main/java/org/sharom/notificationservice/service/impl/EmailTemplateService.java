package org.sharom.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.sharom.notificationservice.service.EmailService;
import org.sharom.notificationservice.service.MessageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {

    private final EmailService emailService;
    private final MessageService messageService;

//    public void sendVerificationCode(String to, String code) {
//
//        String subject = messageService.get("email.verification.subject");
//
//        String bodyTemplate = messageService.get("email.verification.body");
//
//        String text = bodyTemplate.formatted(code);
//
//        emailService.sendEmail(to, subject, text);
//    }

    public void sendWelcomeEmail(String to, String username) {

        String subject = messageService.get("email.welcome.subject");

        String bodyTemplate = messageService.get("email.welcome.body");

        String text = bodyTemplate.formatted(username);

        emailService.sendEmail(to, subject, text);
    }
}