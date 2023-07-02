package com.dingdong.api.community.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyInfoInCommunityDto {

    @Schema(description = "유저 ID")
    private Long userId;

    @Schema(description = "행성 내 주민증에 등록한 유저 닉네임")
    private String nickname;

    @Schema(description = "행성 내 주민증에 등록한 유저 프로필 이미지 Url")
    private String profileImageUrl;

    public static MyInfoInCommunityDto of(Long userId, String nickname, String profileImageUrl) {
        return MyInfoInCommunityDto.builder()
                .userId(userId)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
