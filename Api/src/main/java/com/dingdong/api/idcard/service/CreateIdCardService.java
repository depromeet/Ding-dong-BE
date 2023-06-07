package com.dingdong.api.idcard.service;


import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.idcard.controller.request.CreateIdCardRequest;
import com.dingdong.api.idcard.dto.CreateKeywordDto;
import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import com.dingdong.domain.domains.community.domain.Community;
import com.dingdong.domain.domains.idcard.adaptor.IdCardAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.idcard.domain.entity.Keyword;
import com.dingdong.domain.domains.idcard.validator.IdCardValidator;
import com.dingdong.domain.domains.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreateIdCardService {

    private final UserHelper userHelper;

    private final IdCardAdaptor idCardAdaptor;

    private final IdCardValidator idCardValidator;

    private final CommunityAdaptor communityAdaptor;

    @Transactional
    public void execute(CreateIdCardRequest request) {

        // access token으로 유저 잡아옴
        User currentUser = userHelper.getCurrentUser();

        Community community =
                findAndValidateCommunity(request.getCommunityId(), currentUser.getId());

        IdCard saveIdCard =
                createAndSaveIdCard(
                        community.getId(),
                        currentUser,
                        request.getNickname(),
                        request.getAboutMe());

        // idCard keyword insert
        saveIdCard.updateKeywords(createKeywords(request.getKeywords(), saveIdCard.getId()));
    }

    /**
     * idCard 생성 시 커뮤니티 찾고 해당 커뮤니티에 유저가 주민증을 만들었는지 여부 검사
     *
     * @param communityId 커뮤니티 id
     * @param currentUserId 요청 유저 id
     * @return 찾은 community 객체
     */
    private Community findAndValidateCommunity(Long communityId, Long currentUserId) {
        // community validation
        Community community = communityAdaptor.find(communityId);

        // 이미 등록한 주민증이 있는지 검증
        idCardValidator.isAlreadyCreateCommunityIdCard(community.getId(), currentUserId);

        return community;
    }

    /**
     * idCard 생성 및 저장
     *
     * @param communityId 커뮤니티 id
     * @param currentUser 요청 유저 객체
     * @param nickname request body 닉네임
     * @param aboutMe request body 자기소개
     * @return 생성된 idCard 객체
     */
    private IdCard createAndSaveIdCard(
            Long communityId, User currentUser, String nickname, String aboutMe) {
        // idCard entity 생성
        IdCard idCard =
                IdCard.createIdCard(
                        communityId,
                        currentUser.getId(),
                        nickname,
                        aboutMe,
                        currentUser.getCharacter());

        // idCard save
        return idCardAdaptor.save(idCard);
    }

    /**
     * keyword 리스트 생성
     *
     * @param keywordDtos request body keyword dto 리스트
     * @param idCardId 생성된 idCard id
     * @return 생성된 keyword 리스트
     */
    private List<Keyword> createKeywords(List<CreateKeywordDto> keywordDtos, Long idCardId) {
        return keywordDtos.stream()
                .map(
                        keywordDto ->
                                Keyword.toEntity(
                                        keywordDto.getTitle(),
                                        keywordDto.getContent(),
                                        idCardId,
                                        keywordDto.getImageUrl()))
                .toList();
    }
}
