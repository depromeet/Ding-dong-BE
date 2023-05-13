package com.demo.core.exception;


import com.demo.core.dto.ErrorDetail;

@FunctionalInterface
public interface BaseErrorCode {

    ErrorDetail getErrorDetail();

}
