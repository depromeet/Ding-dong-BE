package com.dingdong.infrastructure.client.feign.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoUserInfoResponse {

    private String id;
    private KakaoProperties properties;

    @JsonProperty(value = "kakao_account")
    private KakaoAcount kakaoAcount;

    @Getter
    public static class KakaoProperties {
        private String nickname;

        @JsonProperty(value = "profile_image")
        private String profileImage;

        @JsonProperty(value = "thumbnail_image")
        private String thumbnailImage;
    }

    @Getter
    public static class KakaoAcount {
        private String email;

        @JsonProperty(value = "age_range")
        private String ageRange;

        private String birthday;
        private String gender;
    }
}
