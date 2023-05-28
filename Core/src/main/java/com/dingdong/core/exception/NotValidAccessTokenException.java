package com.dingdong.core.exception;

public class NotValidAccessTokenException extends BaseException {

    private static final BaseException EXCEPTION = new NotValidAccessTokenException();

    public NotValidAccessTokenException() {
        super(GlobalException.NOT_VALID_ACCESS_TOKEN_ERROR);
    }
}
