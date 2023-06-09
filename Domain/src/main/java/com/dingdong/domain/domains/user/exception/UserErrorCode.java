package com.dingdong.domain.domains.user.exception;

import static com.dingdong.core.consts.StaticVal.BAD_REQUEST;
import static com.dingdong.core.consts.StaticVal.NOT_FOUND;

import com.dingdong.core.annotation.ExplainError;
import com.dingdong.core.dto.ErrorDetail;
import com.dingdong.core.exception.BaseErrorCode;
import java.lang.reflect.Field;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    @ExplainError("존재하지 않는 사용자의 정보를 조회할 때 발생하는 오류입니다.")
    NOT_FOUND_USER(NOT_FOUND, "User-404-1", "존재하지 않는 사용자입니다."),
    @ExplainError("온보딩 캐릭터 정보 저장 시 존재하지 않는 캐릭터 정보를 저장할 때 발생하는 오류입니다.")
    NOT_FOUND_CHARACTER_TYPE(NOT_FOUND, "User-404-2", "존재하지 않는 캐릭터 타입입니다."),
    @ExplainError("커뮤니티를 생성한 관리자 유저가 탈퇴 하려 할 때 발생하는 오류입니다.")
    ADMIN_USER_WITHDRAWAL_ERROR(BAD_REQUEST, "User-400-1", "어드민 유저는 탈퇴 할 수 없습니다.");

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
