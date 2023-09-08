package com.dingdong.domain.domains.idcard.adaptor;


import com.dingdong.core.annotation.Adaptor;
import com.dingdong.domain.domains.idcard.domain.entity.Nudge;
import com.dingdong.domain.domains.idcard.repository.NudgeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class NudgeAdaptor {

    private final NudgeRepository nudgeRepository;

    public void save(Nudge nudge) {
        nudgeRepository.save(nudge);
    }

    public Optional<Nudge> findNudge(Long communityId, Long fromUserId, Long toUserId) {
        return nudgeRepository.findNudgeByCommunityIdAndFromUserIdAndToUserId(
                communityId, fromUserId, toUserId);
    }
}
