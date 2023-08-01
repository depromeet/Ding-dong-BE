package com.dingdong.domain.domains.idcard.adaptor;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_ID_CARD;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_VALID_ID_CARD_USER;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.idcard.repository.IdCardRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@Adaptor
@RequiredArgsConstructor
public class IdCardAdaptor {

    private final IdCardRepository idCardRepository;

    public IdCard findById(Long idCardId) {
        return idCardRepository
                .findById(idCardId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_ID_CARD));
    }

    public IdCard findByIdAndUser(Long idCardId, Long userId) {
        return idCardRepository
                .findByIdAndUserInfo_UserId(idCardId, userId)
                .orElseThrow(() -> new BaseException(NOT_VALID_ID_CARD_USER));
    }

    public IdCard save(IdCard idCard) {
        return idCardRepository.save(idCard);
    }

    public Optional<IdCard> findByUserAndCommunity(Long communityId, Long userId) {
        return idCardRepository.findByCommunityIdAndUserInfo_UserId(communityId, userId);
    }

    public Slice<IdCard> findIdCardByConditionInPage(Long communityId, Pageable pageable) {
        return idCardRepository.findIdCardByConditionInPage(communityId, pageable);
    }

    public List<IdCard> findByUserId(Long userId) {
        return idCardRepository.findAllByUserInfo_UserId(userId);
    }

    public void deleteAll(List<IdCard> idCards) {
        idCardRepository.deleteAllInBatch(idCards);
    }
}
