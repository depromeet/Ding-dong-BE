package com.dingdong.domain.domains.idcard.repository;

import static com.dingdong.domain.domains.idcard.domain.entity.QIdCard.idCard;
import static com.dingdong.domain.domains.idcard.domain.entity.QKeyword.keyword;

import com.dingdong.domain.common.util.SliceUtil;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@RequiredArgsConstructor
public class IdCardRepositoryImpl implements IdCardRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<IdCard> findIdCardByConditionInPage(Long communityId, Pageable pageable) {
        List<IdCard> idCards =
                queryFactory
                        .selectFrom(idCard)
                        .leftJoin(idCard.keywords, keyword)
                        .fetchJoin()
                        .where(idCard.communityId.eq(communityId))
                        .orderBy(idCard.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(idCards, pageable);
    }
}
