package org.sharom.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.sharom.notificationservice.dto.PushRequest;
import org.sharom.notificationservice.entity.DeviceToken;
import org.sharom.notificationservice.entity.Notification;
import org.sharom.notificationservice.entity.ReceiverType;
import org.sharom.notificationservice.producer.PushEvent;
import org.sharom.notificationservice.producer.PushEventProducer;
import org.sharom.notificationservice.repository.DeviceTokenRepository;
import org.sharom.notificationservice.service.NotificationHandler;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PushHandler implements NotificationHandler<PushRequest> {

    private final PushEventProducer producer;
    private final DeviceTokenRepository deviceTokenRepository;
    private static final int PUSH_BATCH_SIZE = 400;

    @Override
    public void handle(PushRequest request) {
        if (CollectionUtils.isEmpty(request.userIds()) || request.receiverType() == ReceiverType.ALL) {
            return;
        }

        List<String> tokens = deviceTokenRepository.findByUserIdIn(request.userIds())
                .stream()
                .map(DeviceToken::getToken)
                .distinct()
                .toList();

        if (CollectionUtils.isEmpty(tokens)) {
            return;
        }

        PushEvent event = new PushEvent(
                UUID.randomUUID(),
                request.notification().getId(),
                tokens,
                0
        );

        producer.send(event);
    }

    @Override
    public Class<PushRequest> getType() {
        return PushRequest.class;
    }



    private void fanoutAllTokens(Notification notification) {

        UUID lastId = new UUID(0L, 0L);

        while (true) {

            List<DeviceToken> tokens =
                    deviceTokenRepository.findNextBatch(lastId, PageRequest.ofSize(1000));

            if (tokens.isEmpty()) break;

            sendInChunks(notification, tokens.stream()
                    .map(DeviceToken::getToken)
                    .distinct()
                    .toList());

            lastId = tokens.get(tokens.size() - 1).getId();
        }
    }


    private void sendInChunks(Notification notification, List<String> tokens) {

        for (int i = 0; i < tokens.size(); i += PUSH_BATCH_SIZE) {

            List<String> batch = tokens.subList(i, Math.min(i + PUSH_BATCH_SIZE, tokens.size()));

            PushEvent event = new PushEvent(
                    UUID.randomUUID(),
                    notification.getId(),
                    batch,
                    0
            );
            log.info("Sending push batch: size={}", batch.size());
            producer.send(event);
        }
    }
}
