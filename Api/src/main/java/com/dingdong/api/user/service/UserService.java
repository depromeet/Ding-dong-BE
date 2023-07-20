package com.dingdong.api.user.service;

import static com.dingdong.domain.domains.idcard.domain.enums.CharacterType.findCharacterType;
import static com.dingdong.domain.domains.user.exception.UserErrorCode.ADMIN_USER_WITHDRAWAL_ERROR;

import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.user.controller.request.UserCharacterRequest;
import com.dingdong.api.user.dto.UserProfileDto;
import com.dingdong.core.consts.StaticVal;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import com.dingdong.domain.domains.community.domain.entity.Community;
import com.dingdong.domain.domains.community.domain.entity.UserJoinCommunity;
import com.dingdong.domain.domains.community.validator.CommunityValidator;
import com.dingdong.domain.domains.idcard.adaptor.CommentAdaptor;
import com.dingdong.domain.domains.idcard.adaptor.IdCardAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import com.dingdong.domain.domains.idcard.domain.model.Character;
import com.dingdong.domain.domains.image.adaptor.ImageAdaptor;
import com.dingdong.domain.domains.image.domain.entity.DeleteImage;
import com.dingdong.domain.domains.notification.adaptor.NotificationAdaptor;
import com.dingdong.domain.domains.user.domain.entity.User;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserHelper userHelper;
    private final IdCardAdaptor idCardAdaptor;
    private final CommentAdaptor commentAdaptor;
    private final ImageAdaptor imageAdaptor;
    private final CommunityAdaptor communityAdaptor;
    private final CommunityValidator communityValidator;
    private final NotificationAdaptor notificationAdaptor;

    public UserProfileDto getUserProfile() {
        List<Community> userJoinCommunities =
                communityAdaptor.findByUserJoin(userHelper.getCurrentUser());
        return UserProfileDto.of(userHelper.getCurrentUser(), userJoinCommunities);
    }

    @Transactional
    public void saveUserCharacter(UserCharacterRequest request) {
        User user = userHelper.getCurrentUser();
        CharacterType characterType = findCharacterType(request.getCharacter());
        user.updateCharacter(Character.toEntity(characterType));
        user.updateProfileImage(StaticVal.getDefaultProfileImage(String.valueOf(characterType)));

        // workAround (유저 회원가입 시 행성 1번 자동 가입)
        if (!communityValidator.validateUserJoinDefaultCommunity(user.getId(), 1L)) {
            communityAdaptor.userJoinCommunity(UserJoinCommunity.toEntity(user.getId(), 1L));
        }
    }

    @Transactional
    public void deleteUser() {
        User currentUser = userHelper.getCurrentUser();

        if (communityAdaptor.existsAdminByUserId(currentUser.getId())) {
            throw new BaseException(ADMIN_USER_WITHDRAWAL_ERROR);
        }

        deleteUserNotification(currentUser.getId());

        List<IdCard> idCards = idCardAdaptor.findByUserId(currentUser.getId());

        deleteUserIdCards(idCards);
        // 유저 삭제
        currentUser.withdraw();
    }

    private void deleteUserNotification(Long toUserId) {
        notificationAdaptor.deleteAll(notificationAdaptor.findByToUserId(toUserId));
    }

    private void deleteUserIdCards(List<IdCard> idCards) {
        List<Long> idCardIds = idCards.stream().map(IdCard::getId).toList();

        deleteUserComments(idCardIds);
        deleteIdCardsProfileImages(idCards);

        idCardAdaptor.deleteAll(idCards);
    }

    private void deleteUserComments(List<Long> idCardIds) {
        List<Comment> comments =
                idCardIds.stream()
                        .map(commentAdaptor::findAllByIdCard)
                        .flatMap(Collection::stream)
                        .toList();
        comments.forEach(Comment::delete);
    }

    private void deleteIdCardsProfileImages(List<IdCard> idCards) {
        List<String> profileImages = idCards.stream().map(IdCard::getProfileImageUrl).toList();
        imageAdaptor.saveAll(profileImages.stream().map(DeleteImage::toEntity).toList());
    }

    @Transactional
    public void removeUserCharacter() {
        userHelper.getCurrentUser().updateCharacter(null);
    }
}
