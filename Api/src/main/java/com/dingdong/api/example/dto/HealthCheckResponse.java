package com.dingdong.api.example.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HealthCheckResponse {

    private final String health;

    public static HealthCheckResponse from(String health) {
        return HealthCheckResponse.builder().health(health).build();
    }
}
