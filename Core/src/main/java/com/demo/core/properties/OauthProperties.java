package com.demo.core.properties;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth2")
public class OauthProperties {

    private OauthSecret kakao;

    @Getter
    @Setter
    public static class OauthSecret {
        private String baseUrl;
        private String clientId;
        private String clientSecret;
        private String redirectUrl;
        private String appId;
        private String adminKey;
    }

    // base url
    public String getKakaoBaseUrl() {
        return kakao.getBaseUrl();
    }

    // rest api key
    public String getKakaoClientId() {
        return kakao.getClientId();
    }

    // redirect url
    public String getKakaoRedirectUrl() {
        return kakao.getRedirectUrl();
    }

    // secret key
    public String getKakaoClientSecret() {
        return kakao.getClientSecret();
    }

    // native app key
    public String getKakaoAppId() {
        return kakao.getAppId();
    }
}
