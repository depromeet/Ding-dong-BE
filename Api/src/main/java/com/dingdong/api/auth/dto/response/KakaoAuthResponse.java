package com.dingdong.api.auth.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoAuthResponse {

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonProperty(value = "expires_in")
    private String expiresIn;

    @JsonProperty(value = "scope")
    private String scope;

    @JsonProperty(value = "refresh_token_expires_in")
    private String refreshTokenExpiresIn;

    public String toAccessToken(String accessToken) {
        return "Bearer " + accessToken;
    }
}
