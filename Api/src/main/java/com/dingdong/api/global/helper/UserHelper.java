package com.dingdong.api.global.helper;

import static com.dingdong.core.exception.GlobalException.NOT_FOUND_USER;

import com.dingdong.api.config.security.SecurityUtils;
import com.dingdong.core.annotation.Helper;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.user.domain.User;
import com.dingdong.domain.domains.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;

@Helper
@RequiredArgsConstructor
public class UserHelper {

    private final UserRepository userRepository;

    public Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }

    public User getCurrentUser() {
        return userRepository
                .findById(getCurrentUserId())
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));
    }
}
