package com.dingdong.domain.domains.idcard.adaptor;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_ID_CARD;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.idcard.repository.IdCardRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class IdCardAdaptor {

    private final IdCardRepository idCardRepository;

    public IdCard findById(Long idCardId) {
        return idCardRepository
                .findById(idCardId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_ID_CARD));
    }

    public IdCard save(IdCard idCard) {
        return idCardRepository.save(idCard);
    }

    public IdCard findByUserAndCommunity(Long communityId, Long userId) {
        return idCardRepository
                .findByCommunityIdAndUserInfo_UserId(communityId, userId)
                .orElse(null);
    }
}
