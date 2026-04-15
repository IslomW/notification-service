package org.sharom.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.sharom.notificationservice.dto.NotificationDTO;
import org.sharom.notificationservice.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount() {
        return ResponseEntity.ok(notificationService.getUnreadCount());
    }

    @GetMapping
    public ResponseEntity<NotificationDTO> getNotificationById(@RequestParam UUID id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @GetMapping
    public ResponseEntity<Page<NotificationDTO>> getAllNotification(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(notificationService.getAllUserNotifications(pageable));
    }

    @PostMapping("/mark")
    public ResponseEntity<Void> markAsRead(@RequestParam UUID notificationId) {
        notificationService.markAsReadById(notificationId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/mark-all")
    public ResponseEntity<Void> markAsReadAll() {
        notificationService.markAsReadAllNotifications();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
