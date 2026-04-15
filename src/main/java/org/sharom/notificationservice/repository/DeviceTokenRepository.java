package org.sharom.notificationservice.repository;

import org.sharom.notificationservice.entity.DeviceToken;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, UUID> {

    List<DeviceToken> findByUserIdIn(List<UUID> userId);

    Optional<DeviceToken> findDeviceTokenByUserId(UUID userId);

    @Query("""
                SELECT d FROM DeviceToken d
                WHERE d.id > :lastId
                ORDER BY d.id ASC
            """)
    List<DeviceToken> findNextBatch(UUID lastId, Pageable pageable);

    void deleteDeviceTokenByToken(String token);
}
