package com.dingdong.api.auth.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "로그인/회원가입 후 Response")
@Getter
@Builder
public class AuthResponse {

    @Schema(description = "액세스 토큰")
    private String accessToken;

    @Schema(description = "리프레시 토큰")
    private String refreshToken;

    @Schema(description = "로그인한 사용자 ID")
    private Long userId;

    @Schema(description = "액세스 토큰의 유효 기간(초 단위, 1시간)")
    private long accessTokenExpireDate;

    public static AuthResponse of(
            String accessToken, String refreshToken, Long userId, long accessTokenExpireDate) {
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userId)
                .accessTokenExpireDate(accessTokenExpireDate)
                .build();
    }
}
