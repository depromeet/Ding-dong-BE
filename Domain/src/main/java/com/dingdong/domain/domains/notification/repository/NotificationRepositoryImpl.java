package com.dingdong.domain.domains.notification.repository;

import static com.dingdong.domain.domains.community.domain.entity.QCommunity.community;
import static com.dingdong.domain.domains.idcard.domain.entity.QComment.comment;
import static com.dingdong.domain.domains.idcard.domain.entity.QCommentReply.commentReply;
import static com.dingdong.domain.domains.idcard.domain.entity.QIdCard.idCard;
import static com.dingdong.domain.domains.notification.domain.entity.QNotification.notification;

import com.dingdong.domain.common.util.SliceUtil;
import com.dingdong.domain.domains.notification.domain.entity.Notification;
import com.dingdong.domain.domains.notification.domain.vo.NotificationVO;
import com.dingdong.domain.domains.notification.domain.vo.QNotificationVO;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

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
                .leftJoin(comment)
                .on(notification.content.commentId.eq(comment.id))
                .leftJoin(commentReply)
                .on(notification.content.commentId.eq(commentReply.id))
                .where(
                        notification.toUserId.eq(userId),
                        notification.createdAt.after(NotificationRepositoryImpl.FIVE_WEEKS_AGO))
                .groupBy(notification.id);
    }

    @Override
    public Slice<NotificationVO> findNotificationByConditionInPage(Long userId, Pageable pageable) {
        List<NotificationVO> notificationVOs =
                buildQuery(userId)
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
                                        community.id,
                                        comment.content,
                                        commentReply.id,
                                        commentReply.content,
                                        notification.content.idCardId))
                        .orderBy(notification.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return SliceUtil.createSliceWithoutPageable(notificationVOs);
    }

    @Override
    public List<NotificationVO> findNotificationByCondition(Long userId) {
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
                                community.id,
                                comment.content,
                                commentReply.id,
                                commentReply.content,
                                notification.content.idCardId))
                .orderBy(notification.id.desc())
                .fetch();
    }
}
