package com.dingdong.domain.domains.notification.repository;


import com.dingdong.domain.domains.notification.domain.entity.Notification;
import com.dingdong.domain.domains.notification.domain.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository
        extends JpaRepository<Notification, Long>, NotificationRepositoryExtension {
    boolean existsByUserIdAndNotificationStatus(Long userId, NotificationStatus notificationStatus);
}
