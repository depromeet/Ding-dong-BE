package com.dingdong.domain.domains.notification.repository;


import com.dingdong.domain.domains.notification.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository
        extends JpaRepository<Notification, Long>, NotificationRepositoryExtension {}
