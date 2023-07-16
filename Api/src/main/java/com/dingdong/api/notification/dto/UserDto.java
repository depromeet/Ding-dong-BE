package com.dingdong.api.notification.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    @Schema(description = "상대방 유저 주민증 고유값")
    private Long fromIdCard;

    @Schema(description = "상대방 프로필 이미지")
    private String fromUserProfileImageUrl;

    @Schema(description = "상대방 닉네임")
    private String fromUserNickname;
}
