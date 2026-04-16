package org.sharom.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "client_notifications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "notification_id"})
)
@SuperBuilder
@Getter
@Setter
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
