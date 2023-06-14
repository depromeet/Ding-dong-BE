package com.dingdong.domain.domains.user.domain.adaptor;

import static com.dingdong.core.exception.GlobalException.NOT_FOUND_USER;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.user.domain.User;
import com.dingdong.domain.domains.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class UserAdaptor {
    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_USER));
    }
}
