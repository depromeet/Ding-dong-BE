package com.dingdong.domain.domains.community.repository;

import static com.dingdong.domain.domains.community.domain.entity.QUserJoinCommunity.userJoinCommunity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    @Override
    public long userCountByCommunityId(Long communityId) {
        return queryFactory
                .select(userJoinCommunity.count())
                .from(userJoinCommunity)
                .where(userJoinCommunity.communityId.eq(communityId))
                .fetchOne();
    }
}
