package com.dingdong.api.user.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserInfoRequest {
    @Schema(description = "이메일")
    private String email;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "성별")
    private String gender;

    @Schema(description = "나이대")
    private String ageRange;

    @Schema(description = "프로필 이미지 url")
    private String profileImageUrl;
}
