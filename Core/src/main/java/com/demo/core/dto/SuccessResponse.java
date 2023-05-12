package com.demo.core.dto;


import lombok.Getter;

@Getter
public class SuccessResponse {

    private final boolean success = true;
    private final int statusCode;
    private final Object data;

    public SuccessResponse(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}
