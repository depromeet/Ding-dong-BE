package com.dingdong.domain.domains.notification.exception;

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
public enum NotificationErrorCode implements BaseErrorCode {
    @ExplainError("존재하지 않는 알림을 조회했을 경우 발생하는 오류입니다.")
    NOT_FOUND_NOTIFICATION(NOT_FOUND, "Notification-404-1", "존재하지 않는 알림입니다."),

    @ExplainError("알림 수정 권한이 없을때 발생하는 오류입니다.")
    NO_AUTHORITY_UPDATE_NOTIFICATION(403, "Notification-403-1", "알림 수정 권한이 없습니다.");

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
