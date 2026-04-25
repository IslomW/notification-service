package org.sharom.notificationservice.dto;

public sealed interface NotificationRequest permits InAppRequest, PushRequest, EmailRequest, SmsRequest {
}