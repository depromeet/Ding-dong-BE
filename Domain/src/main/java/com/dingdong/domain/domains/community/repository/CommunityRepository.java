package com.dingdong.domain.domains.community.repository;


import com.dingdong.domain.domains.community.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    boolean existsCommunityByInvitationCode(String invitationCode);
}
