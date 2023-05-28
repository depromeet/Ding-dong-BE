package com.dingdong.core.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDetail {

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    public static ErrorDetail of(Integer statusCode, String errorCode, String reason) {
        return ErrorDetail.builder()
                .statusCode(statusCode)
                .errorCode(errorCode)
                .reason(reason)
                .build();
    }
}
