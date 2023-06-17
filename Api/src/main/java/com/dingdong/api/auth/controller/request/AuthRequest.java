package com.dingdong.api.auth.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AuthRequest {
    @Schema(description = "인가 코드")
    private String authCode;

    @Schema(description = "리다이렉트 URI")
    private String redirectUri;
}
