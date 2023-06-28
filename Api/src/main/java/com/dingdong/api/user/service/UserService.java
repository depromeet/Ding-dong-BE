package com.dingdong.api.user.service;

import static com.dingdong.domain.domains.idcard.domain.enums.CharacterType.findCharacterType;

import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.user.controller.request.UserCharacterRequest;
import com.dingdong.api.user.dto.UserProfileDto;
import com.dingdong.domain.domains.idcard.domain.model.Character;
import com.dingdong.domain.domains.user.domain.User;
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

    public UserProfileDto getUserProfile() {
        User user = userHelper.getCurrentUser();
        return UserProfileDto.from(user, user.getCharacter() != null);
    }

    @Transactional
    public void saveUserCharacter(UserCharacterRequest request) {
        userHelper
                .getCurrentUser()
                .updateCharacter(Character.toEntity(findCharacterType(request.getCharacter())));
    }
}
