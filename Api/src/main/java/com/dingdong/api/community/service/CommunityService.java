package com.dingdong.api.community.service;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_ID_CARD;

import com.dingdong.api.community.dto.CommunityDetailsDto;
import com.dingdong.api.community.dto.CommunityListDto;
import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.idcard.dto.IdCardDetailsDto;
import com.dingdong.api.idcard.dto.KeywordDto;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import com.dingdong.domain.domains.community.domain.Community;
import com.dingdong.domain.domains.idcard.adaptor.IdCardAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.user.domain.User;
import com.dingdong.domain.domains.user.domain.adaptor.UserAdaptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityAdaptor communityAdaptor;
    private final IdCardAdaptor idCardAdaptor;
    private final UserAdaptor userAdaptor;

    private final UserHelper userHelper;

    public CommunityDetailsDto getCommunityDetails(Long communityId) {
        Community community = communityAdaptor.findById(communityId);

        return CommunityDetailsDto.of(community, community.getIdCards().size());
    }

    public List<CommunityListDto> getUserCommunityList(Long userId) {
        User user = userAdaptor.findById(userId);

        return user.getCommunities().stream()
                .map(community -> CommunityListDto.from(community, community.getIdCards().size()))
                .toList();
    }

    /** 행성에 있는 해당 유저 주민증 상세 조회 */
    public IdCardDetailsDto getUserIdCardDetails(Long communityId) {
        Long currentUserId = userHelper.getCurrentUserId();

        IdCard idCard =
                idCardAdaptor
                        .findByUserAndCommunity(communityId, currentUserId)
                        .orElseThrow(() -> new BaseException(NOT_FOUND_ID_CARD));

        List<KeywordDto> keywordDtos = idCard.getKeywords().stream().map(KeywordDto::of).toList();

        return IdCardDetailsDto.of(idCard, keywordDtos);
    }
}
