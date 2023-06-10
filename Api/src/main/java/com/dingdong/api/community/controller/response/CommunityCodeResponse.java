package com.dingdong.api.community.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommunityCodeResponse {

    @Schema(description = "행성 초대 코드", example = "555555")
    private String invitationCode;
}
