package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdCardRepository extends JpaRepository<IdCard, Long> {

    Optional<IdCard> findByCommunityIdAndUserInfo_UserId(Long communityId, Long userId);
}
