package org.sharom.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "contents",
        indexes = @Index(name = "idx_notification_lang", columnList = "notification_id, lang")
)
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Content extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private Lang lang;

    private String title;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;
}
