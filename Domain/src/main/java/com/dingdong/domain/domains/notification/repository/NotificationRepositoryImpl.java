package com.dingdong.domain.domains.notification.repository;

import static com.dingdong.domain.domains.community.domain.entity.QCommunity.community;
import static com.dingdong.domain.domains.idcard.domain.entity.QComment.comment;
import static com.dingdong.domain.domains.idcard.domain.entity.QCommentLike.commentLike;
import static com.dingdong.domain.domains.idcard.domain.entity.QIdCard.idCard;
import static com.dingdong.domain.domains.notification.domain.entity.QNotification.notification;

import com.dingdong.domain.domains.notification.domain.entity.Notification;
import com.dingdong.domain.domains.notification.domain.enums.NotificationType;
import com.dingdong.domain.domains.notification.domain.vo.NotificationVO;
import com.dingdong.domain.domains.notification.domain.vo.QNotificationVO;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryExtension {
    private final JPAQueryFactory queryFactory;
    private static final Timestamp FIVE_WEEKS_AGO =
            Timestamp.valueOf(LocalDateTime.now().minusWeeks(5));

    private JPAQuery<Notification> buildQuery(Long userId) {
        return queryFactory
                .selectFrom(notification)
                .join(community)
                .on(notification.content.communityId.eq(community.id))
                .join(idCard)
                .on(notification.fromUserIdCardId.eq(idCard.id))
                .where(
                        notification.toUserId.eq(userId),
                        notification.createdAt.after(NotificationRepositoryImpl.FIVE_WEEKS_AGO))
                .groupBy(notification.id);
    }

    @Override
    public List<NotificationVO> getCommentsNotification(Long userId) {
        return buildQuery(userId)
                .select(
                        new QNotificationVO(
                                notification.id,
                                notification.notificationType,
                                notification.notificationStatus,
                                notification.createdAt,
                                community.id,
                                community.name,
                                notification.fromUserIdCardId,
                                idCard.userInfo.profileImageUrl,
                                idCard.userInfo.nickname,
                                comment.id,
                                comment.content,
                                notification.content.idCardId))
                .join(comment)
                .on(notification.content.commentId.eq(comment.id))
                .where(notification.notificationType.eq(NotificationType.ID_CARD_COMMENT))
                .orderBy(notification.id.desc())
                .fetch();
    }

    //    @Override
    //    public List<NotificationVO> getCommentRepliesNotification(Long userId) {
    //        return buildQuery(userId)
    //                .select(
    //                        new QNotificationVO(
    //                                notification.id,
    //                                notification.notificationType,
    //                                notification.notificationStatus,
    //                                notification.createdAt,
    //                                community.id,
    //                                community.name,
    //                                notification.fromUserIdCardId,
    //                                idCard.userInfo.profileImageUrl,
    //                                idCard.userInfo.nickname,
    //                                commentReply.id,
    //                                commentReply.content,
    //                                notification.content.idCardId))
    //                .join(commentReply)
    //                .on(notification.content.commentId.eq(commentReply.id))
    //                .where(notification.notificationType.eq(NotificationType.COMMENT_REPLY))
    //                .orderBy(notification.id.desc())
    //                .fetch();
    //    }

    @Override
    public List<NotificationVO> getCommentLikesNotification(Long userId) {
        return buildQuery(userId)
                .select(
                        new QNotificationVO(
                                notification.id,
                                notification.notificationType,
                                notification.notificationStatus,
                                notification.createdAt,
                                community.id,
                                community.name,
                                notification.fromUserIdCardId,
                                idCard.userInfo.profileImageUrl,
                                idCard.userInfo.nickname,
                                comment.id,
                                comment.content,
                                notification.content.idCardId))
                .join(comment)
                .on(notification.content.commentId.eq(comment.id))
                .join(commentLike)
                .on(commentLike.commentId.eq(comment.id))
                .where(notification.notificationType.eq(NotificationType.COMMENT_LIKE))
                .orderBy(notification.id.desc())
                .fetch();
    }

    //    @Override
    //    public List<NotificationVO> getCommentReplyLikesNotification(Long userId) {
    //        return buildQuery(userId)
    //                .select(
    //                        new QNotificationVO(
    //                                notification.id,
    //                                notification.notificationType,
    //                                notification.notificationStatus,
    //                                notification.createdAt,
    //                                community.id,
    //                                community.name,
    //                                notification.fromUserIdCardId,
    //                                idCard.userInfo.profileImageUrl,
    //                                idCard.userInfo.nickname,
    //                                commentReply.id,
    //                                commentReply.content,
    //                                notification.content.idCardId))
    //                .join(commentReply)
    //                .on(notification.content.commentId.eq(commentReply.id))
    //                .join(commentReplyLike)
    //                .on(commentReplyLike.commentReplyId.eq(commentReply.id))
    //                .where(notification.notificationType.eq(NotificationType.COMMENT_REPLY_LIKE))
    //                .orderBy(notification.id.desc())
    //                .fetch();
    //    }
}
