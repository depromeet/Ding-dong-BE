package com.dingdong.api.auth.dto.response;


import com.dingdong.domain.domains.user.domain.User;
import lombok.Getter;

@Getter
public class UserInfoResponse {

    private Long userId;
    private String email;
    private String nickname;
    private String gender;
    private String ageRange;
    private String profileImageUrl;

    private UserInfoResponse(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.gender = user.getGenderType().name();
        this.ageRange = user.getAgeRange();
        this.profileImageUrl = user.getProfileImageUrl();
    }

    public static UserInfoResponse of(User user) {
        return new UserInfoResponse(user);
    }
}
