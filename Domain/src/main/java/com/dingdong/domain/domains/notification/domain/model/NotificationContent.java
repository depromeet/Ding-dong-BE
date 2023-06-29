package com.dingdong.domain.domains.notification.domain.model;


import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationContent {
    private Long communityId;

    private Long fromUserId;

    private Long commentId;

    private NotificationContent(Long communityId, Long fromUserId, Long commentId) {
        this.communityId = communityId;
        this.fromUserId = fromUserId;
        this.commentId = commentId;
    }

    public static NotificationContent create(Long communityId, Long fromUserId, Long commentId) {
        return new NotificationContent(communityId, fromUserId, commentId);
    }
}
