package com.dingdong.core.dto;


import lombok.Getter;

@Getter
public class ErrorResponse {

    private final boolean success = false;
    private final int statusCode;
    private final String errorCode;
    private final String reason;

    public ErrorResponse(ErrorDetail errorDetail) {
        this.statusCode = errorDetail.getStatusCode();
        this.errorCode = errorDetail.getErrorCode();
        this.reason = errorDetail.getReason();
    }
}
