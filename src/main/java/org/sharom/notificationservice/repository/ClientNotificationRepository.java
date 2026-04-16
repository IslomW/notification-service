package org.sharom.notificationservice.repository;

import jakarta.transaction.Transactional;
import org.sharom.notificationservice.dto.NotificationDTO;
import org.sharom.notificationservice.entity.ClientNotification;
import org.sharom.notificationservice.entity.Lang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClientNotificationRepository extends JpaRepository<ClientNotification, UUID> {

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


    @Query("""
            SELECT new org.sharom.notificationservice.dto.NotificationDTO(
                n.id,
                c.title,
                c.body,
                cn.isRead,
                cn.sentAt
            )
            FROM ClientNotification cn
            JOIN cn.notification n
            JOIN n.contents c
            WHERE n.id = :id 
            AND c.lang = :lang
            """)
    Optional<NotificationDTO> findNotificationDtoByNoteId(UUID id, Lang lang);


    @Modifying
    @Transactional
    @Query("""
                UPDATE ClientNotification cn
                SET cn.isRead = true,
                    cn.status = org.sharom.notificationservice.entity.Status.SENT,
                    cn.readAt = CURRENT_TIMESTAMP
                WHERE cn.userId = :userId
                  AND cn.isRead = false
            """)
    void markAllAsRead(UUID userId);

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO client_notifications (
                id,
                user_id,
                notification_id,
                is_read,
                read_at,
                status,
                sent_at,
                created_at
            )
            SELECT
                gen_random_uuid(),
                :userId,
                n.id,
                true,
                now(),
                'SENT',
                n.created_at,
                now()
            FROM notifications n
            WHERE n.id = :notificationId
            ON CONFLICT (user_id, notification_id)
            DO UPDATE SET
                is_read = true,
                read_at = now()
            """, nativeQuery = true)
    void findOrCreateAndMarkRead(UUID userId, UUID notificationId);

}
