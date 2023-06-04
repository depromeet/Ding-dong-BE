package com.dingdong.infrastructure.client.feign;


import com.dingdong.infrastructure.client.feign.config.KakaoFeignConfiguration;
import com.dingdong.infrastructure.client.feign.dto.response.KakaoAuthResponse;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "KakaoAuthFeignClient",
        url = "${client.kakao.authUrl}",
        configuration = KakaoFeignConfiguration.class)
public interface KakaoAuthFeignClient {

    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoAuthResponse getKakaoAuth(@RequestBody Map<String, ?> forms);
}
