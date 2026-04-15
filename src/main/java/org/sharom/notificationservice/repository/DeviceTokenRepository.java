package org.sharom.notificationservice.repository;

import org.sharom.notificationservice.entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, UUID> {

    List<UUID> findDeviceTokenByUserId(List<UUID> userId);
}
