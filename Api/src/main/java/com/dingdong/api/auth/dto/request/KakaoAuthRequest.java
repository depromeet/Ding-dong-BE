package com.dingdong.api.auth.dto.request;


import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class KakaoAuthRequest {

    public static Map<String, String> getFormData(
            String authCode, String clientId, String clientSecret, String redirectUri) {
        Map<String, String> form = new HashMap<>();
        form.put("code", authCode);
        form.put("client_id", clientId);
        form.put("client_secret", clientSecret);
        form.put("redirect_uri", redirectUri);
        form.put("grant_type", "authorization_code");
        return form;
    }
}
