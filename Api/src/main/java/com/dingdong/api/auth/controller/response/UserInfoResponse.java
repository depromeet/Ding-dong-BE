package com.dingdong.api.auth.controller.response;


import com.dingdong.domain.domains.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private Long userId;
    private String email;
    private String gender;
    private String ageRange;

    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(
                user.getId(), user.getEmail(), user.getGenderType().name(), user.getAgeRange());
    }
}