package com.dingdong.core.exception;

public class ExpiredRefreshTokenException extends BaseException {

    public static final BaseException EXCEPTION = new ExpiredRefreshTokenException();

    private ExpiredRefreshTokenException() {
        super(GlobalException.EXPIRED_REFRESH_TOKEN_ERROR);
    }
}
