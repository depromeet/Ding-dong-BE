package com.demo.core.exception;


import com.demo.core.dto.ErrorDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private BaseErrorCode errorCode;

    public ErrorDetail getErrorDetail() {
        return this.errorCode.getErrorDetail();
    }
}