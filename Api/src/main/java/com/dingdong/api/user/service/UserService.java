package com.dingdong.api.user.service;

import static com.dingdong.domain.domains.idcard.domain.enums.CharacterType.findCharacterType;

import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.user.controller.request.UserCharacterRequest;
import com.dingdong.api.user.dto.UserProfileDto;
import com.dingdong.domain.domains.idcard.domain.model.Character;
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
        return UserProfileDto.from(userHelper.getCurrentUser());
    }

    @Transactional
    public void saveUserCharacter(UserCharacterRequest request) {
        userHelper
                .getCurrentUser()
                .updateCharacter(Character.toEntity(findCharacterType(request.getCharacter())));
    }

    @Transactional
    public void removeUserCharacter() {
        userHelper.getCurrentUser().updateCharacter(null);
    }
}
