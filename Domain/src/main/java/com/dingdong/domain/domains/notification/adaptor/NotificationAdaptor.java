package com.dingdong.domain.domains.notification.adaptor;

import static com.dingdong.domain.domains.notification.exception.NotificationErrorCode.NOT_FOUND_NOTIFICATION;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.notification.domain.entity.Notification;
import com.dingdong.domain.domains.notification.domain.vo.NotificationVO;
import com.dingdong.domain.domains.notification.repository.NotificationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class NotificationAdaptor {
    private final NotificationRepository notificationRepository;

    public Notification findById(Long notificationId) {
        return notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_NOTIFICATION));
    }

    public List<Notification> findByToUserId(Long toUserId) {
        return notificationRepository.findAllByToUserId(toUserId);
    }

    public void deleteAll(List<Notification> notifications) {
        notificationRepository.deleteAllInBatch(notifications);
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<NotificationVO> getCommentsNotification(Long userId) {
        return notificationRepository.getCommentsNotification(userId);
    }

    //    public List<NotificationVO> getCommentRepliesNotification(Long userId) {
    //        return notificationRepository.getCommentRepliesNotification(userId);
    //    }

    public List<NotificationVO> getCommentLikesNotification(Long userId) {
        return notificationRepository.getCommentLikesNotification(userId);
    }

    //    public List<NotificationVO> getCommentReplyLikesNotification(Long userId) {
    //        return notificationRepository.getCommentReplyLikesNotification(userId);
    //    }
}
