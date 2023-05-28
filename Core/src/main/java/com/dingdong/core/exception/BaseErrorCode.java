package com.dingdong.core.exception;


import com.dingdong.core.dto.ErrorDetail;

@FunctionalInterface
public interface BaseErrorCode {

    ErrorDetail getErrorDetail();

}
