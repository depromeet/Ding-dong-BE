package com.dingdong.domain.domains.community.repository;


import com.dingdong.domain.domains.community.domain.entity.Community;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository
        extends JpaRepository<Community, Long>, CommunityRepositoryExtension {
    boolean existsCommunityByInvitationCode(String invitationCode);

    boolean existsCommunityByName(String name);

    Optional<Community> findByInvitationCode(String invitationCode);

    boolean existsCommunityById(Long id);

    List<Community> findAllByIdIn(List<Long> communitiesId);
}
