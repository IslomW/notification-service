package org.sharom.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.sharom.notificationservice.dto.EmailRequest;
import org.sharom.notificationservice.dto.InAppRequest;
import org.sharom.notificationservice.dto.SmsRequest;
import org.sharom.notificationservice.service.impl.NotificationCommandService;
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

    private final NotificationCommandService commandService;


    @PostMapping("/in-app")
    public ResponseEntity<Void> inApp(@RequestBody InAppRequest request){
        commandService.send(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/email")
    public ResponseEntity<Void> email(@RequestBody EmailRequest request){
        commandService.send(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/sms")
    public ResponseEntity<Void> sms(@RequestBody SmsRequest request){
        commandService.send(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
