package com.dingdong.api.user.service;


import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.user.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserHelper userHelper;

    public UserProfileDto getUserProfile() {
        return UserProfileDto.from(userHelper.getCurrentUser());
    }
}
