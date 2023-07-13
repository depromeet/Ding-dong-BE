package com.dingdong.domain.domains.community.exception;

import static com.dingdong.core.consts.StaticVal.*;

import com.dingdong.core.annotation.ExplainError;
import com.dingdong.core.dto.ErrorDetail;
import com.dingdong.core.exception.BaseErrorCode;
import java.lang.reflect.Field;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommunityErrorCode implements BaseErrorCode {
    @ExplainError("존재하지 않는 행성을 찾을 때 발생하는 오류입니다.")
    NOT_FOUND_COMMUNITY(NOT_FOUND, "Community-404-1", "존재하지 않는 행성입니다."),
    @ExplainError("관리자가 아닌 다른 사용자가 행성 꾸미기를 시도할 때 발생하는 오류입니다.")
    NO_AUTHORITY_UPDATE_COMMUNITY(FORBIDDEN, "Community-403-1", "행성 수정 권한이 없습니다."),
    @ExplainError("행성을 만들 때 이미 존재하는 행성 이름을 사용하려고 할 때 발생하는 오류입니다.")
    ALREADY_EXIST_COMMUNITY_NAME(BAD_REQUEST, "Community-400-1", "이미 존재하는 행성 이름입니다."),
    @ExplainError("행성 이름이 최소 길이인 1글자 미만일 때 발생하는 오류입니다.")
    MIN_LIMIT_COMMUNITY_NAME(BAD_REQUEST, "Community-400-2", "행성 이름 최소 길이는 1글자입니다."),
    @ExplainError("행성 이름이 최대 길이인 16글자를 초과할 때 발생하는 오류입니다.")
    MAX_LIMIT_COMMUNITY_NAME(BAD_REQUEST, "Community-400-3", "행성 이름 최대 길이는 16글자입니다."),
    @ExplainError("이미 가입된 행성에 가입 시도를 할 때 발생하는 오류입니다.")
    ALREADY_JOIN_COMMUNITY(REDIRECTION, "Community-300-1", "이미 가입된 행성입니다."),
    @ExplainError("가입하지 않은 행성에서 행성 떠나기를 시도할 때 발생하는 오류입니다.")
    NOT_JOIN_COMMUNITY(FORBIDDEN, "Community-403-2", "행성 정보 조회 및 수정 권한이 없습니다.");

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
