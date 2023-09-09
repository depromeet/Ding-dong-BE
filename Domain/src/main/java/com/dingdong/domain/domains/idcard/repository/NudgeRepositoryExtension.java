package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.model.NudgeVo;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface NudgeRepositoryExtension {
    List<NudgeVo> getNudges(Long toUserId, Pageable pageable);
}
