package com.dingdong.api.user.controller.response;


import com.dingdong.api.user.dto.UserProfileDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    private Long userId;
    private String email;
    private String gender;
    private String ageRange;
    private String profileImageUrl;

    public static UserProfileResponse from(UserProfileDto userProfileDto) {
        return UserProfileResponse.builder()
                .userId(userProfileDto.getUserId())
                .email(userProfileDto.getEmail())
                .gender(userProfileDto.getGender())
                .ageRange(userProfileDto.getAgeRange())
                .profileImageUrl(userProfileDto.getProfileImageUrl())
                .build();
    }
}
