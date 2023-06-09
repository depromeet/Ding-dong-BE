package com.dingdong.domain.domains.user.domain.adaptor;

import static com.dingdong.domain.domains.user.exception.UserErrorCode.NOT_FOUND_USER;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.user.domain.UserRepository;
import com.dingdong.domain.domains.user.domain.entity.User;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class UserAdaptor {
    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_USER));
    }
}
