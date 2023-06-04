package com.dingdong.api.config;


import static com.dingdong.core.consts.StaticVal.BAD_REQUEST;

import com.dingdong.core.dto.ErrorDetail;
import com.dingdong.core.dto.ErrorResponse;
import com.dingdong.core.exception.BaseErrorCode;
import com.dingdong.core.exception.BaseException;
import com.dingdong.core.exception.GlobalException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> internalServerExceptionHandle(
            Exception e, HttpServletRequest req) throws IOException {
        String url =
                UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(req))
                        .build()
                        .toUriString();

        log.error("INTERNAL_SERVER_ERROR", e);
        GlobalException internalServerError = GlobalException.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(internalServerError.getErrorDetail());

        return ResponseEntity.status(HttpStatus.valueOf(internalServerError.getStatusCode()))
                .body(errorResponse);
    }

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> baseExceptionHandle(
            BaseException e, HttpServletRequest req) {
        BaseErrorCode code = e.getErrorCode();
        ErrorDetail errorDetail = code.getErrorDetail();
        ErrorResponse errorResponse = new ErrorResponse(errorDetail);
        return ResponseEntity.status(HttpStatus.valueOf(errorDetail.getStatusCode()))
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> argumentNotValidHandle(
        MethodArgumentNotValidException exception) {
        ErrorDetail reason =
            ErrorDetail.builder()
                .statusCode(BAD_REQUEST)
                .errorCode("Global-400-1")
                .reason(
                    exception
                        .getBindingResult()
                        .getAllErrors()
                        .get(0)
                        .getDefaultMessage())
                .build();
        ErrorResponse errorResponse = new ErrorResponse(reason);
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }
}
