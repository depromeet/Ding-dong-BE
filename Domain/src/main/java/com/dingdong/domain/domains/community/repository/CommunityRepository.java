package com.dingdong.domain.domains.community.repository;


import com.dingdong.domain.domains.community.domain.entity.Community;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    boolean existsCommunityByInvitationCode(String invitationCode);

    boolean existsCommunityByName(String name);

    Optional<Community> findByInvitationCode(String invitationCode);

    @Query("SELECT COUNT(u) FROM User u JOIN u.communities c WHERE c.id = :communityId")
    int userCountByCommunityId(Long communityId);
}
