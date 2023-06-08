package com.dingdong.api.user.service;

import static com.dingdong.core.exception.GlobalException.*;

import com.dingdong.domain.domains.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserInfoResponse updateUserInfo(UserInfoRequest request) {
        User user = findUser();
        checkDuplicateNickname(request.getNickname(), user);
        user.updateNickname(request.getNickname());

        return UserInfoResponse.from(user);
    }

    private User findUser() {
        return userRepository
                .findById(SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));
    }

    private void checkDuplicateNickname(String nickname, User user) {
        if (isNicknameChanged(nickname, user) && isNicknameExists(nickname)) {
            throw new BaseException(ALREADY_EXISTS_NICKNAME);
        }
    }

    private boolean isNicknameChanged(String nickname, User user) {
        return !nickname.equals(user.getNickname());
    }

    private boolean isNicknameExists(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
