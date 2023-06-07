package com.dingdong.domain.domains.community.exception;

import static com.dingdong.core.consts.StaticVal.NOT_FOUND;

import com.dingdong.core.dto.ErrorDetail;
import com.dingdong.core.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommunityErrorCode implements BaseErrorCode {
    /** 커뮤니티 Aggregate 관련 에러 코드 */
    NOT_FOUND_COMMUNITY(NOT_FOUND, "Community-404-1", "존재하지 않는 커뮤니티입니다.");

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.of(statusCode, errorCode, reason);
    }
}
