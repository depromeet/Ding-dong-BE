package com.dingdong.domain.domains.idcard.adaptor;


import com.dingdong.core.annotation.Adaptor;
import com.dingdong.domain.domains.idcard.domain.entity.Nudge;
import com.dingdong.domain.domains.idcard.domain.model.NudgeVo;
import com.dingdong.domain.domains.idcard.repository.NudgeRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@Adaptor
@RequiredArgsConstructor
public class NudgeAdaptor {

    private final NudgeRepository nudgeRepository;

    public void save(Nudge nudge) {
        nudgeRepository.save(nudge);
    }

    public Optional<Nudge> findNudge(Long fromUserId, Long toUserId) {
        return nudgeRepository.findNudgeByFromUserIdCardIdAndToUserIdCardId(fromUserId, toUserId);
    }

    public List<NudgeVo> getNudges(Long toUserId, Pageable pageable) {
        return nudgeRepository.getNudges(toUserId, pageable);
    }
}
