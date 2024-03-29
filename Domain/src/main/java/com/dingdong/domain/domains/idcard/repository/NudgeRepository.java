package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.entity.Nudge;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NudgeRepository extends JpaRepository<Nudge, Long>, NudgeRepositoryExtension {

    Optional<Nudge> findNudgeByFromUserIdCardIdAndToUserIdCardId(
            Long fromUserIdCardId, Long toUserIdCardId);
}
