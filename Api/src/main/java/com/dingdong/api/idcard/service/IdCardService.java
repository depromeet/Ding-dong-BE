package com.dingdong.api.idcard.service;


import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.idcard.controller.request.CreateIdCardRequest;
import com.dingdong.api.idcard.controller.request.UpdateIdCardRequest;
import com.dingdong.api.idcard.dto.CreateKeywordDto;
import com.dingdong.api.idcard.dto.IdCardDetailsDto;
import com.dingdong.api.idcard.dto.KeywordDto;
import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import com.dingdong.domain.domains.community.domain.entity.Community;
import com.dingdong.domain.domains.community.validator.CommunityValidator;
import com.dingdong.domain.domains.idcard.adaptor.CommentAdaptor;
import com.dingdong.domain.domains.idcard.adaptor.IdCardAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.idcard.domain.entity.Keyword;
import com.dingdong.domain.domains.idcard.validator.CommentValidator;
import com.dingdong.domain.domains.idcard.validator.IdCardValidator;
import com.dingdong.domain.domains.image.adaptor.ImageAdaptor;
import com.dingdong.domain.domains.image.domain.entity.DeleteImage;
import com.dingdong.domain.domains.user.domain.entity.User;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IdCardService {

    private final UserHelper userHelper;

    private final IdCardAdaptor idCardAdaptor;

    private final IdCardValidator idCardValidator;

    private final CommentValidator commentValidator;

    private final CommunityAdaptor communityAdaptor;

    private final CommunityValidator communityValidator;

    private final ImageAdaptor imageAdaptor;

    private final CommentAdaptor commentAdaptor;

    /** 주민증 세부 조회 */
    public IdCardDetailsDto getIdCardDetails(Long idCardsId) {
        IdCard idCard = idCardAdaptor.findById(idCardsId);

        validateIsJoinUser(userHelper.getCurrentUser(), idCard.getCommunityId());

        List<KeywordDto> keywordDtos = idCard.getKeywords().stream().map(KeywordDto::of).toList();

        return IdCardDetailsDto.of(idCard, keywordDtos);
    }

    /** 댓글 개수 조회 */
    public int getCommentCount(Long idCardId) {
        IdCard idCard = idCardAdaptor.findById(idCardId);

        validateIsJoinUser(userHelper.getCurrentUser(), idCard.getCommunityId());

        return commentAdaptor.findAllByIdCard(idCard.getId()).size();
    }

    /** 주민증 생성 */
    @Transactional
    public Long createIdCard(CreateIdCardRequest request) {

        // access token으로 유저 잡아옴
        User currentUser = userHelper.getCurrentUser();

        Community community =
                findAndValidateCommunity(request.getCommunityId(), currentUser.getId());

        validateIsJoinUser(userHelper.getCurrentUser(), community.getId());

        IdCard saveIdCard =
                createAndSaveIdCard(
                        community.getId(),
                        currentUser,
                        request.getProfileImageUrl(),
                        request.getNickname(),
                        request.getAboutMe());

        community.addIdCard(saveIdCard);

        List<Keyword> keywords = createKeywords(request.getKeywords(), saveIdCard.getId());

        saveIdCard.updateKeywords(keywords);

        return saveIdCard.getId();
    }

    /** 주민증 수정 */
    @Transactional
    public Long updateIdCard(Long idCardId, UpdateIdCardRequest request) {
        User currentUser = userHelper.getCurrentUser();
        IdCard idCard = idCardAdaptor.findByIdAndUser(idCardId, currentUser.getId());

        deleteKeywords(idCard);

        List<Keyword> keywords = createKeywords(request.getKeywords(), idCardId);

        IdCard updateIdCard =
                idCard.updateIdCard(
                        request.getProfileImageUrl(),
                        request.getNickname(),
                        request.getAboutMe(),
                        keywords);

        return updateIdCard.getId();
    }

    /** idCard 생성 시 커뮤니티 찾고 해당 커뮤니티에 유저가 주민증을 만들었는지 여부 검사 */
    private Community findAndValidateCommunity(Long communityId, Long currentUserId) {
        // community validation
        Community community = communityAdaptor.findById(communityId);

        // 이미 등록한 주민증이 있는지 검증
        idCardValidator.isAlreadyCreateCommunityIdCard(community.getId(), currentUserId);

        return community;
    }

    /** idCard 생성 및 저장 */
    private IdCard createAndSaveIdCard(
            Long communityId,
            User currentUser,
            String profileImageUrl,
            String nickname,
            String aboutMe) {

        // idCard entity 생성
        IdCard idCard =
                IdCard.createIdCard(
                        communityId,
                        currentUser.getId(),
                        profileImageUrl,
                        nickname,
                        aboutMe,
                        currentUser.getCharacter());

        // idCard save
        return idCardAdaptor.save(idCard);
    }

    /** keyword 리스트 생성 */
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

    /** keyword 리스트 삭제 */
    private void deleteKeywords(IdCard idCard) {
        List<Keyword> keywords = idCard.getKeywords();

        List<DeleteImage> deleteImages =
                keywords.stream()
                        .map(Keyword::getImagerUrl)
                        .filter(Objects::nonNull)
                        .map(DeleteImage::toEntity)
                        .toList();

        // deleteImage에 추가
        imageAdaptor.saveAll(deleteImages);

        // orphanRemoval 적용
        keywords.clear();
    }

    /** 유저가 해당 커뮤니티에 가입된 상태인지 확인 */
    private void validateIsJoinUser(User user, Long communityId) {
        communityValidator.isExistInCommunity(user, communityId);
    }
}
