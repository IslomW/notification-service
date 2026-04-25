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
    private ReceiverType receiverType;

    @Column(nullable = false)
    private Long readCount = 0L;

    @OneToMany(
            mappedBy = "notification",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Content> contents;



    public void addContent(Content content) {
        contents.add(content);
        content.setNotification(this);
    }
}
