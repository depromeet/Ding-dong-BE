package com.dingdong.api.idcard.service;


import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.idcard.controller.request.CreateIdCardRequest;
import com.dingdong.api.idcard.dto.CreateKeywordDto;
import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import com.dingdong.domain.domains.community.domain.Community;
import com.dingdong.domain.domains.idcard.adaptor.IdCardAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.idcard.domain.entity.Keyword;
import com.dingdong.domain.domains.idcard.domain.vo.UserInfo;
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

        // community validation
        Community community = communityAdaptor.find(request.getCommunityId());

        // 이미 등록한 주민증이 있는지 검증
        idCardValidator.isAlreadyCreateCommunityIdCard(community.getId(), currentUser.getId());

        // request body로 userInfo Vo 생성
        UserInfo userInfo =
                UserInfo.create(
                        currentUser.getId(),
                        request.getNickname(),
                        request.getAboutMe(),
                        currentUser.getCharacter());

        // idCard entity 생성
        IdCard idCard = IdCard.toEntity(community.getId(), userInfo);

        // idCard save
        IdCard saveIdCard = idCardAdaptor.save(idCard);

        // idCard keyword insert
        saveIdCard.updateKeywords(toKeywords(request.getKeywords(), saveIdCard.getId()));
    }

    private List<Keyword> toKeywords(List<CreateKeywordDto> keywordDtos, Long idCardId) {
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
