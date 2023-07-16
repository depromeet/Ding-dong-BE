package com.dingdong.domain.domains.notification.domain.model;


import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 알림의 타겟이 되는 필드들 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationContent {
    private Long communityId;

    private Long idCardId;

    private Long commentId;

    private NotificationContent(Long communityId, Long idCardId, Long commentId) {
        this.communityId = communityId;
        this.idCardId = idCardId;
        this.commentId = commentId;
    }

    public static NotificationContent create(Long communityId, Long idCardId, Long commentId) {
        return new NotificationContent(communityId, idCardId, commentId);
    }
}
