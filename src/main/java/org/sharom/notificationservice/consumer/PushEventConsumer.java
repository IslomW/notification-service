//package org.sharom.notificationservice.consumer;
////
////import com.google.firebase.messaging.BatchResponse;
////import com.google.firebase.messaging.FirebaseMessaging;
////import com.google.firebase.messaging.MulticastMessage;
////import com.google.firebase.messaging.SendResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.sharom.notificationservice.dto.NotificationDTO;
//import org.sharom.notificationservice.entity.Lang;
//import org.sharom.notificationservice.producer.PushEvent;
//import org.sharom.notificationservice.repository.ClientNotificationRepository;
//import org.sharom.notificationservice.repository.DeviceTokenRepository;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class PushEventConsumer {
//
//    private final FirebaseMessaging firebaseMessaging;
//    private final DeviceTokenRepository deviceTokenRepository;
//    private final ClientNotificationRepository clientNotificationRepository;
//    private final KafkaTemplate<String, PushEvent> kafkaTemplate;
//
//    private static final int MAX_RETRY = 3;
//
//
//    @KafkaListener(topics = "push-topic", groupId = "push-group")
//    public void consume(PushEvent event) {
//
//        log.info("Received push event: {}", event.eventId());
//
//        try {
//            NotificationDTO content =
//                    clientNotificationRepository.findNotificationDtoById(event.notificationId(), Lang.RU)
//                            .orElseThrow(() -> new RuntimeException("content.not.found"));
//
//            sendBatch(event.tokens(), content);
//
//        } catch (Exception e) {
//            log.error("Push failed: {}", event.eventId(), e);
//            handleRetry(event);
//        }
//
//    }
//
//    @KafkaListener(topics = "push-retry-topic", groupId = "push-retry-group")
//    public void retry(PushEvent event) {
//
//        try {
//            NotificationDTO content =
//                    clientNotificationRepository.findNotificationDtoById(event.notificationId(), Lang.RU)
//                            .orElseThrow(() -> new RuntimeException("content.not.found"));
//
//            sendBatch(event.tokens(), content);
//
//        } catch (Exception e) {
//            handleRetry(event);
//        }
//    }
//
//
//    private void sendBatch(List<String> tokens, NotificationDTO content) {
//
//        MulticastMessage message = MulticastMessage.builder()
//                .addAllTokens(tokens)
//                .setNotification(
//                        com.google.firebase.messaging.Notification.builder()
//                                .setTitle(content.title())
//                                .setBody(content.body())
//                                .build()
//                )
//                .build();
//
//        // try catch
////        BatchResponse response = firebaseMessaging.sendMulticast(message);
//        BatchResponse response = new BatchResponse() {
//            @Override
//            public List<SendResponse> getResponses() {
//                return List.of();
//            }
//
//            @Override
//            public int getSuccessCount() {
//                return 0;
//            }
//
//            @Override
//            public int getFailureCount() {
//                return 0;
//            }
//        };
//
//        handleResponse(response, tokens);
//    }
//
//    private void handleRetry(PushEvent event) {
//
//        if (event.retryCount() >= MAX_RETRY) {
//
//            log.error("Send to DLQ: {}", event.eventId());
//
//            kafkaTemplate.send("push-dlq-topic", event);
//            return;
//        }
//
//        PushEvent retryEvent = new PushEvent(
//                event.eventId(),
//                event.notificationId(),
//                event.tokens(),
//                event.retryCount() + 1
//        );
//
//        kafkaTemplate.send("push-retry-topic", retryEvent);
//    }
//
//
//    private void handleResponse(BatchResponse response, List<String> tokens) {
//
//        for (int i = 0; i < response.getResponses().size(); i++) {
//
//            SendResponse r = response.getResponses().get(i);
//
//            if (!r.isSuccessful()) {
//
//                String failedToken = tokens.get(i);
//
//                log.warn("Invalid token: {}", failedToken);
//
//                deviceTokenRepository.deleteDeviceTokenByToken(failedToken);
//            }
//        }
//    }
//
//
//}
