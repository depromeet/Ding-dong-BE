package com.demo.core.exception;

public class OuterServerUnauthorizedException extends BaseException {

    public static final BaseException EXCEPTION = new OuterServerUnauthorizedException();

    private OuterServerUnauthorizedException() {
        super(GlobalException.OUTER_SERVER_UNAUTHORIZED_EXCEPTION);
    }
}
