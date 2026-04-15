package org.sharom.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.sharom.notificationservice.dto.CreateNotificationRequest;
import org.sharom.notificationservice.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard/notification")
@RequiredArgsConstructor
public class DashboardController {

    private final NotificationService notificationService;


    @PostMapping
    public ResponseEntity<Void> createNotification(@RequestBody CreateNotificationRequest request){
        notificationService.createNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
