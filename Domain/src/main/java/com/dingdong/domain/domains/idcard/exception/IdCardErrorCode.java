package com.dingdong.domain.domains.idcard.exception;

import static com.dingdong.core.consts.StaticVal.BAD_REQUEST;
import static com.dingdong.core.consts.StaticVal.NOT_FOUND;

import com.dingdong.core.dto.ErrorDetail;
import com.dingdong.core.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IdCardErrorCode implements BaseErrorCode {
    /** 주민증 Aggregate 관련 에러 코드 */
    NOT_FOUND_ID_CARD(NOT_FOUND, "IdCard-404-1", "존재하지 않는 주민증입니다."),
    ALREADY_EXIST_ID_CARD(BAD_REQUEST, "IdCard-400-1", "이미 해당 커뮤니티에 주민증을 등록했습니다."),
    NOT_FOUND_COMMENT(NOT_FOUND, "Comment-404-1", "존재하지 않는 댓글입니다."),
    NOT_VALID_ID_CARD_COMMENT(BAD_REQUEST, "IdCard-400-2", "해당 주민증의 댓글이 아닙니다.");

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.of(statusCode, errorCode, reason);
    }
}
