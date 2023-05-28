package com.dingdong.core.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OuterServerException extends RuntimeException {

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;
}
