package com.dingdong.domain.domains.notification.domain.entity;

import static com.dingdong.domain.domains.notification.exception.NotificationErrorCode.NO_AUTHORITY_UPDATE_NOTIFICATION;

import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.notification.domain.enums.NotificationStatus;
import com.dingdong.domain.domains.notification.domain.enums.NotificationType;
import com.dingdong.domain.domains.notification.domain.model.NotificationContent;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_notification")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long toUserId;

    @NotNull private Long fromUserIdCardId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private NotificationType notificationType;

    @Embedded private NotificationContent content;

    @Enumerated(EnumType.STRING)
    @NotNull
    private NotificationStatus notificationStatus = NotificationStatus.UNREAD;

    public void read(Long userId) {
        if (this.toUserId != userId) {
            throw new BaseException(NO_AUTHORITY_UPDATE_NOTIFICATION);
        }
        this.notificationStatus = NotificationStatus.READ;
    }

    private Notification(
            Long toUserId,
            Long fromUserIdCardId,
            NotificationType notificationType,
            NotificationContent content) {
        this.toUserId = toUserId;
        this.fromUserIdCardId = fromUserIdCardId;
        this.notificationType = notificationType;
        this.content = content;
    }

    public static Notification create(
            Long toUserId,
            Long fromUserIdCardId,
            NotificationType notificationType,
            NotificationContent notificationContent) {
        return new Notification(
                toUserId,
                fromUserIdCardId,
                notificationType,
                NotificationContent.create(
                        notificationContent.getCommunityId(),
                        notificationContent.getIdCardId(),
                        notificationContent.getCommentId()));
    }
}
