package com.dingdong.domain.domains.idcard.validator;


import com.dingdong.core.annotation.Validator;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.idcard.adaptor.IdCardAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.idcard.exception.IdCardErrorCode;
import lombok.RequiredArgsConstructor;

@Validator
@RequiredArgsConstructor
public class IdCardValidator {

    private final IdCardAdaptor idCardAdaptor;

    public void isAlreadyCreateCommunityIdCard(Long communityId, Long userId) {
        IdCard idCard = idCardAdaptor.findByUserAndCommunity(communityId, userId);

        if (idCard != null) {
            throw new BaseException(IdCardErrorCode.ALREADY_EXIST_ID_CARD);
        }
    }
}
