package com.dingdong.domain.domains.community.repository;

import static com.dingdong.domain.domains.community.domain.entity.QCommunity.community;
import static com.dingdong.domain.domains.user.domain.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    @Override
    public long userCountByCommunityId(Long communityId) {
        return queryFactory
                .select(user.count())
                .from(user)
                .join(user.communities, community)
                .where(community.id.eq(communityId))
                .fetchOne();
    }
}
