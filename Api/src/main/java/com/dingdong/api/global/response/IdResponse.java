package com.dingdong.api.global.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IdResponse {

    private final Long id;

    public static IdResponse from(Long id) {
        return IdResponse.builder().id(id).build();
    }
}
