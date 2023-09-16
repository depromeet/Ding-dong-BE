package com.dingdong.domain.domains.idcard.validator;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.ALREADY_EXIST_NUDGE;

import com.dingdong.core.annotation.Validator;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.idcard.adaptor.NudgeAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.Nudge;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Validator
@RequiredArgsConstructor
public class NudgeValidator {

    private final NudgeAdaptor nudgeAdaptor;

    public void isAlreadyCreateNudge(Long fromUserId, Long toUserId) {
        Optional<Nudge> nudge = nudgeAdaptor.findNudge(fromUserId, toUserId);

        if (nudge.isPresent()) {
            throw new BaseException(ALREADY_EXIST_NUDGE);
        }
    }
}
