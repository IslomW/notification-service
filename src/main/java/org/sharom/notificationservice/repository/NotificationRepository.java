package org.sharom.notificationservice.repository;

import org.sharom.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    @Query("""
        SELECT 
            (SELECT COUNT(cn) FROM ClientNotification cn 
             WHERE cn.userId = :userId AND cn.isRead = false) 
            + 
            (SELECT COUNT(n) FROM Notification n 
             WHERE n.receiverType = 'ALL' 
             AND n.id NOT IN (
                 SELECT cn2.notification.id FROM ClientNotification cn2 
                 WHERE cn2.userId = :userId
             ))
        """)
    Long countTotalUnread(UUID userId);
}
