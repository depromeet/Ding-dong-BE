package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdCardRepository extends JpaRepository<IdCard, Long>, IdCardRepositoryExtension {

    Optional<IdCard> findByCommunityIdAndUserInfo_UserId(Long communityId, Long userId);

    List<IdCard> findAllByUserInfo_UserId(Long userId);

    Optional<IdCard> findByIdAndUserInfo_UserId(Long idCardId, Long userId);
}
