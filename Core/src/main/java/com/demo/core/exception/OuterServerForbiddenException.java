package com.demo.core.exception;

public class OuterServerForbiddenException extends BaseException {

    public static final BaseException EXCEPTION = new OuterServerForbiddenException();

    private OuterServerForbiddenException() {
        super(GlobalException.OUTER_SERVER_FORBIDDEN_EXCEPTION);
    }
}
