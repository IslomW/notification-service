package org.sharom.notificationservice.producer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PushEventProducer {

    private static final int MAX_RETRY = 3;


    private final KafkaTemplate<String, PushEvent> kafkaTemplate;

    private static final String TOPIC = "push-topic";

    public void send(PushEvent event) {
        kafkaTemplate.send(TOPIC, event.eventId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null){
                       log.error("kafka send failed", ex);
                    }else {
                        log.info("Kafka sent: {}", result.getRecordMetadata());
                    }
                });
    }


    public void handleRetry(PushEvent event) {

        if (event.retryCount() >= MAX_RETRY) {

            log.error("Send to DLQ: {}", event.eventId());

            kafkaTemplate.send("push-dlq-topic", event);
            return;
        }

        PushEvent retryEvent = new PushEvent(
                event.eventId(),
                event.notificationId(),
                event.tokens(),
                event.retryCount() + 1
        );

        kafkaTemplate.send("push-retry-topic", retryEvent);
    }
}
