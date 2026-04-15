package org.sharom.notificationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceToken extends BaseEntity {

    private UUID userId;

    private String deviceId;

    private String appVersion;

    private Platform platform;

    @Column(nullable = false, unique = true)
    private String token;
}
