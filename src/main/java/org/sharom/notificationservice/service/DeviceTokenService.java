package org.sharom.notificationservice.service;

import org.sharom.notificationservice.entity.DeviceToken;

import java.util.List;
import java.util.UUID;

public interface DeviceTokenService {

    List<DeviceToken> getAllUserTokens(UUID userId);

    void addToken(UUID userId, String token);

    void deleteTokenById(UUID id);

}
