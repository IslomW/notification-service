package org.sharom.notificationservice;


import org.sharom.notificationservice.entity.DeviceToken;
import org.sharom.notificationservice.entity.Lang;
import org.sharom.notificationservice.entity.Platform;
import org.sharom.notificationservice.repository.DeviceTokenRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class DataLoader {


//    @Bean
//    CommandLineRunner load(DeviceTokenRepository repo) {
//        return args -> {
//
//            for (int i = 0; i < 1000; i++) {
//
//                DeviceToken token = DeviceToken.builder()
//                        .userId(UUID.randomUUID())
//                        .deviceId("device-" + i)
//                        .appVersion("1.0." + (i % 10))
//                        .platform(i % 2 == 0 ? Platform.ANDROID : Platform.IOS)
//                        .lang(i % 2 == 0 ? Lang.RU : Lang.UZ)
//                        .token("mock-token-" + i) // ⚠️ должен быть уникальный
//                        .build();
//
//                repo.save(token);
//            }
//
//            System.out.println("🔥 1000 mock tokens created");
//        };
//
//    }
}
