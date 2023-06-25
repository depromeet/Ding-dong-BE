package com.dingdong.api.user.controller.response;


import com.dingdong.api.user.dto.UserProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    @Schema(description = "사용자 프로필 정보")
    private UserProfileDto userProfileDto;

    public static UserProfileResponse from(UserProfileDto userProfileDto) {
        return UserProfileResponse.builder().userProfileDto(userProfileDto).build();
    }
}
