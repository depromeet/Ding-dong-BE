package com.dingdong.domain.domains.notification.repository;

import static com.dingdong.domain.domains.community.domain.entity.QCommunity.community;
import static com.dingdong.domain.domains.idcard.domain.entity.QComment.comment;
import static com.dingdong.domain.domains.idcard.domain.entity.QIdCard.idCard;
import static com.dingdong.domain.domains.notification.domain.entity.QNotification.notification;

import com.dingdong.domain.common.util.SliceUtil;
import com.dingdong.domain.domains.notification.domain.vo.NotificationVO;
import com.dingdong.domain.domains.notification.domain.vo.QNotificationVO;
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

    @Override
    public Slice<NotificationVO> findNotificationByConditionInPage(Long userId, Pageable pageable) {
        Timestamp fiveWeeksAgo = Timestamp.valueOf(LocalDateTime.now().minusWeeks(5));

        List<NotificationVO> notificationVOs =
                queryFactory
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
                        .from(notification)
                        .where(notification.userId.eq(userId))
                        .join(community)
                        .on(community.id.eq(notification.content.communityId))
                        .join(idCard)
                        .on(idCard.userInfo.userId.eq(notification.content.fromUserId))
                        .join(comment)
                        .on(notification.content.commentId.eq(comment.id))
                        .where(notification.createdAt.after(fiveWeeksAgo))
                        .orderBy(notification.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(notificationVOs, pageable);
    }
}
