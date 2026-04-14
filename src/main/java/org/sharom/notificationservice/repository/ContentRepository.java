package org.sharom.notificationservice.repository;

import org.sharom.notificationservice.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContentRepository extends JpaRepository<Content, UUID> {
}
