package com.dingdong.domain.domains.notification.domain.vo;


import com.dingdong.domain.domains.notification.domain.enums.NotificationStatus;
import com.dingdong.domain.domains.notification.domain.enums.NotificationType;
import com.querydsl.core.annotations.QueryProjection;
import java.sql.Timestamp;
import lombok.Getter;

@Getter
public class NotificationVO {
    private final Long notificationId;

    private final NotificationType notificationType;

    private final NotificationStatus notificationStatus;

    private final Timestamp createdAt;

    private final Long communityId;

    private final String communityName;

    private final Long fromIdCardId;

    private final String fromUserProfileImageUrl;

    private final String fromUserNickname;

    private final Long commentId;

    private final String comment;

    private final Long replyId;

    private final String reply;

    private final Long IdCardId;

    @QueryProjection
    public NotificationVO(
            Long notificationId,
            NotificationType notificationType,
            NotificationStatus notificationStatus,
            Timestamp createdAt,
            Long communityId,
            String communityName,
            Long fromIdCardId,
            String fromUserProfileImageUrl,
            String fromUserNickname,
            Long commentId,
            String comment,
            Long replyId,
            String reply,
            Long idCardId) {
        this.notificationId = notificationId;
        this.notificationType = notificationType;
        this.notificationStatus = notificationStatus;
        this.createdAt = createdAt;
        this.communityId = communityId;
        this.communityName = communityName;
        this.fromIdCardId = fromIdCardId;
        this.fromUserProfileImageUrl = fromUserProfileImageUrl;
        this.fromUserNickname = fromUserNickname;
        this.commentId = commentId;
        this.comment = comment;
        this.replyId = replyId;
        this.reply = reply;
        this.IdCardId = idCardId;
    }
}
