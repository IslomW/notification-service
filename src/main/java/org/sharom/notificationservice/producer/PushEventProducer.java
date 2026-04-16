package org.sharom.notificationservice.producer;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PushEventProducer {


    private final KafkaTemplate<String, PushEvent> kafkaTemplate;

    private static final String TOPIC = "push-topic";

    public void send(PushEvent event) {
        kafkaTemplate.send(TOPIC, event.eventId().toString(), event);
    }
}
