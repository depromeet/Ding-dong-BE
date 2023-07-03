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

    @Schema(description = "관리자 인지 여부")
    private boolean isAdmin;

    public static MyInfoInCommunityDto of(
            Long userId, String nickname, String profileImageUrl, boolean isAdmin) {
        return MyInfoInCommunityDto.builder()
                .userId(userId)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .isAdmin(isAdmin)
                .build();
    }
}
