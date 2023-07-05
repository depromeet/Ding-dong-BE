package com.dingdong.api.global.helper;

import static com.dingdong.domain.common.consts.Status.N;
import static com.dingdong.domain.domains.user.exception.UserErrorCode.NOT_FOUND_USER;

import com.dingdong.api.config.security.SecurityUtils;
import com.dingdong.core.annotation.Helper;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.user.domain.UserRepository;
import com.dingdong.domain.domains.user.domain.entity.User;
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
                .findByIdAndIsDeleted(getCurrentUserId(), N)
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));
    }
}
