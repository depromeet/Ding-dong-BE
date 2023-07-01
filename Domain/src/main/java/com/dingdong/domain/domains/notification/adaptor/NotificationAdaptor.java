package com.dingdong.domain.domains.notification.adaptor;

import static com.dingdong.domain.domains.notification.exception.NotificationErrorCode.NOT_FOUND_NOTIFICATION;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.notification.domain.entity.Notification;
import com.dingdong.domain.domains.notification.domain.enums.NotificationStatus;
import com.dingdong.domain.domains.notification.domain.vo.NotificationVO;
import com.dingdong.domain.domains.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@Adaptor
@RequiredArgsConstructor
public class NotificationAdaptor {
    private final NotificationRepository notificationRepository;

    public Slice<NotificationVO> findNotificationByConditionInPage(Long userId, Pageable pageable) {
        return notificationRepository.findNotificationByConditionInPage(userId, pageable);
    }

    public Notification findById(Long notificationId) {
        return notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_NOTIFICATION));
    }

    public boolean existsUnreadNotifications(Long userId) {
        return notificationRepository.existsByUserIdAndNotificationStatus(
                userId, NotificationStatus.UNREAD);
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }
}
