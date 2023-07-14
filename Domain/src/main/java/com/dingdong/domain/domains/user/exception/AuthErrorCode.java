package com.dingdong.domain.domains.user.exception;

import static com.dingdong.core.consts.StaticVal.UNAUTHORIZED;

import com.dingdong.core.annotation.ExplainError;
import com.dingdong.core.dto.ErrorDetail;
import com.dingdong.core.exception.BaseErrorCode;
import java.lang.reflect.Field;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {
    @ExplainError("인증에 사용되는 액세스 토큰이 만료되었을 때 발생하는 오류입니다.")
    EXPIRED_TOKEN_ERROR(UNAUTHORIZED, "401-1", "토큰이 만료되었습니다."),
    @ExplainError("인증에 사용되는 토큰의 정보가 올바르지 않을 때 발생하는 오류입니다.")
    INVALID_TOKEN_ERROR(UNAUTHORIZED, "401-2", "올바르지 않은 토큰입니다."),
    @ExplainError("액세스 토큰을 재발급 받기 위한 리프레시 토큰이 만료되었을 때 발생하는 오류입니다.")
    EXPIRED_REFRESH_TOKEN_ERROR(UNAUTHORIZED, "401-3", "리프레시 토큰이 만료되었습니다."),
    @ExplainError("인증에 사용되는 토큰이 헤더에 존재하지 않을 때 발생하는 오류입니다.")
    NOT_VALID_ACCESS_TOKEN_ERROR(UNAUTHORIZED, "401-4", "알맞은 accessToken 을 넣어주세요."),
    @ExplainError("소셜 로그인 이용 시 이메일 수집 동의를 하지 않을 때 발생하는 오류입니다.")
    REQUIRED_EMAIL_COLLECTION_CONSENT(UNAUTHORIZED, "401-5", "소셜 로그인 이용 시 이메일 수집 동의가 필요합니다.");

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.of(statusCode, errorCode, reason);
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getReason();
    }
}
