package com.dingdong.domain.domains.community.repository;


import com.dingdong.domain.domains.community.domain.entity.UserJoinCommunity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJoinCommunityRepository extends JpaRepository<UserJoinCommunity, Long> {

    List<UserJoinCommunity> findAllByUserId(Long userId);

    Optional<UserJoinCommunity> findByUserIdAndCommunityId(Long userId, Long communityId);
}
