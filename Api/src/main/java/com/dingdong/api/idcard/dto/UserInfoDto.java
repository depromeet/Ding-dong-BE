package com.dingdong.api.idcard.dto;


import com.dingdong.domain.domains.idcard.domain.model.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {

    @Schema(description = "유저 id", example = "1")
    private final Long userId;

    @Schema(description = "유저 프로필 이미지", example = "test.com")
    private final String profileImageUrl;

    @Schema(description = "유저 닉네임", example = "민준김")
    private final String nickname;

    public static UserInfoDto from(UserInfo userInfo) {
        return UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .profileImageUrl(userInfo.getProfileImageUrl())
                .nickname(userInfo.getNickname())
                .build();
    }
}
