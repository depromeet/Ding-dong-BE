package com.dingdong.core.exception;

public class OuterServerExpiredTokenException extends BaseException {

    public static final BaseException EXCEPTION = new OuterServerExpiredTokenException();

    private OuterServerExpiredTokenException() {
        super(GlobalException.OUTER_SERVER_EXPIRED_TOKEN_EXCEPTION);
    }
}
