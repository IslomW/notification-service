package org.sharom.notificationservice.dto.event;

public record UserRegisteredEvent(String email,
                                  String name) {
}