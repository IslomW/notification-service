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
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "notification_id"}),
        indexes = {
                @Index(name = "idx_user", columnList = "user_id"),
                @Index(name = "idx_notification", columnList = "notification_id")
        }
)
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClientNotification extends AuditEntity {


    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "is_read")
    private boolean read;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Instant sentAt;

    private Instant readAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;


}
