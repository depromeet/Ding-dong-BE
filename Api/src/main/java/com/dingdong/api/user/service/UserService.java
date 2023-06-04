package com.dingdong.api.user.service;

import static com.dingdong.core.exception.GlobalException.*;

import com.dingdong.api.auth.dto.response.UserInfoResponse;
import com.dingdong.api.config.security.SecurityUtils;
import com.dingdong.api.user.dto.request.UserInfoRequest;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.user.domain.User;
import com.dingdong.domain.domains.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserInfoResponse updateUserInfo(UserInfoRequest request) {
        User user = findUser();
        if (!request.getNickname().equals(user.getNickname())
                && userRepository.existsByNickname(request.getNickname())) {
            throw new BaseException(ALREADY_EXISTS_NICKNAME);
        }
        user.updateNickname(request.getNickname());
        return UserInfoResponse.of(user);
    }

    private User findUser() {
        return userRepository
                .findById(SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));
    }
}
