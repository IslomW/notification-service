package org.sharom.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "notifications")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private NotificationChanel notificationChanel;

    @Enumerated(EnumType.STRING)
    private ReceiverType receiverType;

    private Long readCount;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Content> contents;
}
