package com.dingdong.core.exception;

public class OuterServerBadRequestException extends BaseException {

    public static final BaseException EXCEPTION = new OuterServerBadRequestException();

    private OuterServerBadRequestException() {
        super(GlobalException.OUTER_SERVER_BAD_REQUEST_EXCEPTION);
    }
}
