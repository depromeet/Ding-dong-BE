package com.dingdong.api.user.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserCharacterRequest {

    @Schema(description = "캐릭터 정보")
    private String character;
}
