package com.dingdong.infrastructure.client.feign.config;


import feign.Client;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoFeignConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("Content-Type", "application/x-www-form-urlencoded");
    }

    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }
}
