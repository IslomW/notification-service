package org.sharom.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "client_notifications")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClientNotification extends AuditEntity {


    private UUID userId;
    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Instant sentAt;
    private Instant readAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;


}
