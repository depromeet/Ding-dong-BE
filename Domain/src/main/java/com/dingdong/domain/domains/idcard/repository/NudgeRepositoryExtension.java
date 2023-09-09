package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.model.NudgeVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NudgeRepositoryExtension {
    Slice<NudgeVo> getNudges(Long toUserId, Pageable pageable);
}
