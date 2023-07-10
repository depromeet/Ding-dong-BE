package com.dingdong.domain.domains.idcard.validator;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.ALREADY_EXIST_ID_CARD;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_EXIST_ID_CARD_IN_COMMUNITY;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_VALID_ID_CARD_COMMENT;

import com.dingdong.core.annotation.Validator;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.idcard.adaptor.IdCardAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Validator
@RequiredArgsConstructor
public class IdCardValidator {

    private final IdCardAdaptor idCardAdaptor;

    public void isAlreadyCreateCommunityIdCard(Long communityId, Long userId) {
        Optional<IdCard> idCard = idCardAdaptor.findByUserAndCommunity(communityId, userId);

        if (idCard.isPresent()) {
            throw new BaseException(ALREADY_EXIST_ID_CARD);
        }
    }

    public void isValidIdCardComment(IdCard idCard, Comment comment) {
        if (!Objects.equals(comment.getIdCardId(), idCard.getId())) {
            throw new BaseException(NOT_VALID_ID_CARD_COMMENT);
        }
    }

    public void validateUserIdCardInCommunity(Long communityId, Long userId) {
        idCardAdaptor
                .findByUserAndCommunity(communityId, userId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_ID_CARD_IN_COMMUNITY));
    }
}
