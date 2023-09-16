package com.dingdong.domain.domains.idcard.repository;

import static com.dingdong.domain.domains.idcard.domain.entity.QIdCard.idCard;
import static com.dingdong.domain.domains.idcard.domain.entity.QNudge.nudge;

import com.dingdong.domain.domains.idcard.domain.entity.Nudge;
import com.dingdong.domain.domains.idcard.domain.model.NudgeVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class NudgeRepositoryImpl implements NudgeRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    private JPAQuery<Nudge> buildQuery(Long toUserId) {
        return queryFactory
                .selectFrom(nudge)
                .join(idCard)
                .on(nudge.fromUserIdCardId.eq(idCard.id))
                .groupBy(nudge.id);
    }

    @Override
    public List<NudgeVo> getNudges(Long toUserId, Pageable pageable) {
        return buildQuery(toUserId)
                .select(Projections.constructor(NudgeVo.class, nudge, idCard.userInfo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
    }
}
