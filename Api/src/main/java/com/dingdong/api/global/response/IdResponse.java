package com.dingdong.api.global.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IdResponse {

    @Schema(description = "생성, 수정된 도메인 객체의 id")
    private final Long id;

    public static IdResponse from(Long id) {
        return IdResponse.builder().id(id).build();
    }
}
