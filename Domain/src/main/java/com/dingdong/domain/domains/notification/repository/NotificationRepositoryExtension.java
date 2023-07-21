package com.dingdong.domain.domains.notification.repository;


import com.dingdong.domain.domains.notification.domain.vo.NotificationVO;
import java.util.List;

public interface NotificationRepositoryExtension {
    List<NotificationVO> getCommentsNotification(Long userId);

    List<NotificationVO> getCommentRepliesNotification(Long userId);

    List<NotificationVO> getCommentLikesNotification(Long userId);

    List<NotificationVO> getCommentReplyLikesNotification(Long userId);
}
