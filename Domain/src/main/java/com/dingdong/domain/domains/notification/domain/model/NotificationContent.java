package com.dingdong.domain.domains.notification.domain.model;


import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class NotificationContent {
    private Long communityId;

    private Long fromUserId;

    private Long commentId;
}
