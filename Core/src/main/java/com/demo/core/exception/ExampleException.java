package com.demo.core.exception;

public class ExampleException extends BaseException {

    public static final BaseException EXCEPTION = new ExampleException();

    private ExampleException() {
        super(GlobalException.EXAMPLE_ERROR);
    }
}
