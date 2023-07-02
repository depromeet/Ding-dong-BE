package com.dingdong.api.community.service;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_ID_CARD;

import com.dingdong.api.community.controller.request.CreateCommunityRequest;
import com.dingdong.api.community.controller.request.JoinCommunityRequest;
import com.dingdong.api.community.controller.request.UpdateCommunityRequest;
import com.dingdong.api.community.dto.CommunityDetailsDto;
import com.dingdong.api.community.dto.CommunityIdCardsDto;
import com.dingdong.api.community.dto.CommunityListDto;
import com.dingdong.api.community.dto.MyInfoInCommunityDto;
import com.dingdong.api.community.service.generator.RandomCommunityCodeGeneratorStrategy;
import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.idcard.dto.IdCardDetailsDto;
import com.dingdong.api.idcard.dto.KeywordDto;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.common.util.SliceUtil;
import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import com.dingdong.domain.domains.community.domain.entity.Community;
import com.dingdong.domain.domains.community.domain.model.CommunityImage;
import com.dingdong.domain.domains.community.domain.strategy.GenerateCommunityInvitationCodeStrategy;
import com.dingdong.domain.domains.community.validator.CommunityValidator;
import com.dingdong.domain.domains.idcard.adaptor.IdCardAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.user.domain.User;
import com.dingdong.domain.domains.user.domain.adaptor.UserAdaptor;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityValidator communityValidator;
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

    // 행성 만들기
    @Transactional
    public Long createCommunity(CreateCommunityRequest request) {
        communityValidator.validateDuplicatedCommunityName(request.getName());
        return communityAdaptor
                .save(
                        createCommunityEntity(request.getName(), request.getLogoImageUrl()),
                        userHelper.getCurrentUser())
                .getId();
    }

    // 행성 꾸미기
    @Transactional
    public Long updateCommunity(Long communityId, UpdateCommunityRequest request) {
        Community community = findAndValidateAdminUserInCommunity(communityId);
        updateCommunityEntity(
                community,
                request.getName(),
                request.getLogoImageUrl(),
                request.getCoverImageUrl(),
                request.getDescription());
        return community.getId();
    }

    /** 행성의 모든 주민증 조회 */
    public Slice<CommunityIdCardsDto> getCommunityIdCards(Long communityId, Pageable pageable) {
        Slice<IdCard> idCards = idCardAdaptor.findIdCardByConditionInPage(communityId, pageable);

        return SliceUtil.valueOf(
                idCards.stream()
                        .map(idCard -> CommunityIdCardsDto.of(idCard, idCard.getKeywords()))
                        .toList(),
                pageable);
    }

    /** 행성에 있는 해당 유저 주민증 상세 조회 */
    public IdCardDetailsDto getUserIdCardDetails(Long communityId) {
        User currentUser = userHelper.getCurrentUser();
        communityValidator.isExistInCommunity(currentUser, communityId);

        IdCard idCard =
                idCardAdaptor
                        .findByUserAndCommunity(communityId, currentUser.getId())
                        .orElseThrow(() -> new BaseException(NOT_FOUND_ID_CARD));

        List<KeywordDto> keywordDtos = idCard.getKeywords().stream().map(KeywordDto::of).toList();

        return IdCardDetailsDto.of(idCard, keywordDtos);
    }

    public boolean checkDuplicatedName(String name) {
        communityValidator.validateCommunityNameSize(name);
        return communityAdaptor.isAlreadyExistCommunityName(name);
    }

    public Long validateInvitationCode(String code) {
        return communityAdaptor.findByInvitationCode(code).getId();
    }

    @Transactional
    public void joinCommunity(JoinCommunityRequest request) {
        User user = userHelper.getCurrentUser();
        Community community = communityAdaptor.findById(request.getCommunityId());
        communityValidator.isAlreadyJoinCommunity(user, community.getId());
        user.joinCommunity(community);
    }

    @Transactional
    public void withdrawCommunity(Long communityId) {
        User user = userHelper.getCurrentUser();
        Community community = communityAdaptor.findById(communityId);
        communityValidator.isExistInCommunity(user, communityId);
        user.getCommunities().remove(community);
    }

    public boolean checkForUserIdCardInCommunity(Long communityId) {
        User currentUser = userHelper.getCurrentUser();
        Community community = communityAdaptor.findById(communityId);
        return idCardAdaptor
                .findByUserAndCommunity(community.getId(), currentUser.getId())
                .isPresent();
    }

    public MyInfoInCommunityDto getMyInfoInCommunity(Long communityId) {
        User user = userHelper.getCurrentUser();
        communityValidator.isExistCommunity(communityId);
        communityValidator.isExistInCommunity(user, communityId);

        IdCard idCard =
                idCardAdaptor
                        .findByUserAndCommunity(communityId, user.getId())
                        .orElseThrow(() -> new BaseException(NOT_FOUND_ID_CARD));

        return MyInfoInCommunityDto.of(
                user.getId(), idCard.getNickname(), idCard.getProfileImageUrl());
    }

    private Community findAndValidateAdminUserInCommunity(Long communityId) {
        User currentUser = userHelper.getCurrentUser();
        communityValidator.isExistInCommunity(currentUser, communityId);
        communityValidator.verifyAdminUser(communityId, currentUser.getId());
        return communityAdaptor.findById(communityId);
    }

    private Community createCommunityEntity(String name, String logoImageUrl) {
        return Community.createCommunity(name, logoImageUrl, createCommunityInvitationCode());
    }

    private String createCommunityInvitationCode() {
        return generateRandomAlphanumericCode(new RandomCommunityCodeGeneratorStrategy());
    }

    private String generateRandomAlphanumericCode(
            GenerateCommunityInvitationCodeStrategy strategy) {
        return strategy.generate(getCommunityInvitationCodes());
    }

    private List<String> getCommunityInvitationCodes() {
        return communityAdaptor.findAll().stream()
                .map(Community::getInvitationCode)
                .collect(Collectors.toList());
    }

    private void updateCommunityEntity(
            Community community,
            String name,
            String logoImageUrl,
            String coverImageUrl,
            String description) {
        CommunityImage communityImage =
                CommunityImage.createCommunityImage(logoImageUrl, coverImageUrl);
        community.updateCommunity(name, communityImage, description);
    }
}
