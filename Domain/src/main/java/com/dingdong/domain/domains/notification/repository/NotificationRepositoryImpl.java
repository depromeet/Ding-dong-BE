package com.dingdong.domain.domains.notification.repository;

import static com.dingdong.domain.domains.community.domain.entity.QCommunity.community;
import static com.dingdong.domain.domains.idcard.domain.entity.QComment.comment;
import static com.dingdong.domain.domains.idcard.domain.entity.QIdCard.idCard;
import static com.dingdong.domain.domains.notification.domain.entity.QNotification.notification;

import com.dingdong.domain.common.util.SliceUtil;
import com.dingdong.domain.domains.notification.domain.entity.Notification;
import com.dingdong.domain.domains.notification.domain.enums.NotificationStatus;
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
                .on(notification.content.fromUserId.eq(idCard.userInfo.userId))
                .join(comment)
                .on(notification.content.commentId.eq(comment.id))
                .where(
                        notification.userId.eq(userId),
                        notification.createdAt.after(NotificationRepositoryImpl.FIVE_WEEKS_AGO),
                        comment.userId.ne(userId))
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
                                        notification.content.fromUserId,
                                        idCard.userInfo.profileImageUrl,
                                        idCard.userInfo.nickname,
                                        community.id,
                                        comment.content,
                                        idCard.id))
                        .orderBy(notification.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(notificationVOs, pageable);
    }

    @Override
    public boolean existsUnreadNotification(Long userId) {
        long count =
                buildQuery(userId)
                        .where(notification.notificationStatus.eq(NotificationStatus.UNREAD))
                        .fetchCount();

        return count > 0;
    }
}
