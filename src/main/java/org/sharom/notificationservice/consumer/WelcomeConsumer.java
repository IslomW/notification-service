package org.sharom.notificationservice.consumer;

import lombok.RequiredArgsConstructor;
import org.sharom.notificationservice.dto.event.UserRegisteredEvent;
import org.sharom.notificationservice.service.impl.EmailTemplateService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WelcomeConsumer {

    private final EmailTemplateService emailTemplateService;

    @KafkaListener(
            topics = "welcome-notification-topic",
            groupId = "notification-group"
    )
    public void consume(UserRegisteredEvent event) {

        System.out.println("🔥 KELDI: " + event.email());

        emailTemplateService.sendWelcomeEmail(
                event.email(),
                event.name()
        );
    }
}