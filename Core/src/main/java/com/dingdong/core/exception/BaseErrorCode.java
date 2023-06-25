package com.dingdong.core.exception;


import com.dingdong.core.dto.ErrorDetail;

public interface BaseErrorCode {

    ErrorDetail getErrorDetail();

    String getExplainError() throws NoSuchFieldException;
}
