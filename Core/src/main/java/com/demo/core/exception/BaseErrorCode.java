package com.demo.core.exception;


import com.demo.core.dto.ErrorDetail;

public interface BaseErrorCode {

    public ErrorDetail getErrorDetail();

    //    String getExplainError() throws NoSuchFieldException;
}
