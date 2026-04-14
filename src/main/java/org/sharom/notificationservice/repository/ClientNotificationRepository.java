package org.sharom.notificationservice.repository;

import org.sharom.notificationservice.entity.ClientNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientNotificationRepository extends JpaRepository<ClientNotification, UUID> {
}
