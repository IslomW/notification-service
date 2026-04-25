package org.sharom.notificationservice.repository;

import org.sharom.notificationservice.dto.NotificationDTO;
import org.sharom.notificationservice.entity.Lang;
import org.sharom.notificationservice.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    @Query("""
                SELECT
                    (SELECT COUNT(cn) FROM ClientNotification cn
                     WHERE cn.userId = :userId
                       AND cn.read = false
                    )
                    +
                    (SELECT COUNT(n) FROM Notification n
                     WHERE n.receiverType = org.sharom.notificationservice.entity.ReceiverType.ALL
                       AND NOT EXISTS (
                           SELECT 1 FROM ClientNotification cn2
                           WHERE cn2.notification.id = n.id
                             AND cn2.userId = :userId
                       )
                    )
            """)
    Long countTotalUnread(UUID userId);


    @Query("""
                SELECT n FROM Notification n
                LEFT JOIN ClientNotification cn
                    ON cn.notification.id = n.id AND cn.userId = :userId
                WHERE n.receiverType = org.sharom.notificationservice.entity.ReceiverType.ALL
                  AND cn.id IS NULL
            """)
    List<Notification> findAllGlobalNotLinkedToUser(UUID userId);


    @Query("""
                SELECT new org.sharom.notificationservice.dto.NotificationDTO(
                    n.id,
                    c.title,
                    c.body,
                    COALESCE(cn.read, false),
                    COALESCE(cn.sentAt, n.createdAt)
                )
                FROM Notification n
                JOIN n.contents c
                LEFT JOIN ClientNotification cn
                    ON cn.notification.id = n.id AND cn.userId = :userId
                WHERE c.lang = :lang
                ORDER BY n.createdAt DESC
            """)
    Page<NotificationDTO> findAllForUser(
            UUID userId,
            Lang lang,
            Pageable pageable
    );

}
