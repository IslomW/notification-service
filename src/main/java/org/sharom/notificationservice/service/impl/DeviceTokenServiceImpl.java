package org.sharom.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.sharom.notificationservice.entity.DeviceToken;
import org.sharom.notificationservice.entity.Platform;
import org.sharom.notificationservice.repository.DeviceTokenRepository;
import org.sharom.notificationservice.service.DeviceTokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceTokenServiceImpl implements DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;

    @Override
    public List<DeviceToken> getAllUserTokens(UUID userId) {
        return deviceTokenRepository.findByUserIdIn(List.of(userId));
    }

    @Override
    public void addToken(UUID userId, String token) {
        //get version
        String version = "1.0.0";
        String deviceId = "213564";

        DeviceToken deviceToken = new DeviceToken(
                userId,
                deviceId,
                version,
                Platform.ANDROID,
                token
        );

        deviceTokenRepository.save(deviceToken);
    }

    @Override
    public void deleteTokenById(UUID id) {
        deviceTokenRepository.delete(getTokenByUserId(id));
    }

    @Override
    public DeviceToken getTokenByUserId(UUID userId) {
        return deviceTokenRepository.findDeviceTokenByUserId(userId)
                .orElseThrow(() -> new RuntimeException("token.not.found"));
    }
}
