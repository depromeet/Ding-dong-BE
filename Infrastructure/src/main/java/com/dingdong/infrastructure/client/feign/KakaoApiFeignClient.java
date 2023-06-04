package com.dingdong.infrastructure.client.feign;


import com.dingdong.infrastructure.client.feign.config.KakaoFeignConfiguration;
import com.dingdong.infrastructure.client.feign.dto.response.KakaoUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "KakaoApiFeignClient",
        url = "${client.kakao.apiUrl}",
        configuration = KakaoFeignConfiguration.class)
public interface KakaoApiFeignClient {

    @GetMapping(value = "/v2/user/me")
    KakaoUserInfoResponse getKakaoUserInfo(
            @RequestHeader(name = "Authorization") String accessToken);
}
