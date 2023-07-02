package com.dingdong.api.user.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserCharacterRequest {

    @Schema(description = "캐릭터 정보")
    @NotNull(message = "캐릭터 생성 정보를 입력해주세요.")
    private String character;
}
