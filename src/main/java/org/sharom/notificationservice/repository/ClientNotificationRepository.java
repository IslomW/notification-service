package org.sharom.notificationservice.repository;

import org.sharom.notificationservice.dto.NotificationDTO;
import org.sharom.notificationservice.entity.ClientNotification;
import org.sharom.notificationservice.entity.Lang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClientNotificationRepository extends JpaRepository<ClientNotification, UUID> {
    Optional<ClientNotification> getClientNotificationsById(UUID id);

    @Query("""
    SELECT new org.sharom.notificationservice.dto.NotificationDTO(
        cn.id,
        c.title,
        c.body,
        cn.isRead,
        cn.sentAt
    )
    FROM ClientNotification cn
    JOIN cn.notification n
    JOIN n.contents c
    WHERE cn.id = :id 
    AND c.lang = :lang
    """)
    Optional<NotificationDTO> findNotificationDtoById(UUID id, Lang lang);
}
