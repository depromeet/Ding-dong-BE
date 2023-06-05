package com.dingdong.infrastructure.client.feign.dto.request;


import java.util.Map;
import lombok.Getter;

@Getter
public class KakaoAuthRequest {

    public static Map<String, String> createAuthFormData(
            String authCode, String clientId, String clientSecret, String redirectUri) {
        return Map.of(
                "code", authCode,
                "client_id", clientId,
                "client_secret", clientSecret,
                "redirect_uri", redirectUri,
                "grant_type", "authorization_code");
    }
}
