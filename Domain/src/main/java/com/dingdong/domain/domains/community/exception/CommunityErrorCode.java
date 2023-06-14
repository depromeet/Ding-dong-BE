package com.dingdong.domain.domains.community.exception;

import static com.dingdong.core.consts.StaticVal.*;

import com.dingdong.core.dto.ErrorDetail;
import com.dingdong.core.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommunityErrorCode implements BaseErrorCode {
    /** 커뮤니티 Aggregate 관련 에러 코드 */
    NOT_FOUND_COMMUNITY(NOT_FOUND, "Community-404-1", "존재하지 않는 커뮤니티입니다."),
    ALREADY_EXIST_COMMUNITY_INVITATION_CODE(
            BAD_REQUEST, "Community-400-1", "이미 존재하는 커뮤니티 초대 코드입니다."),
    NO_AUTHORITY_UPDATE_COMMUNITY(FORBIDDEN, "Community-403-1", "커뮤니티 수정 권한이 없습니다.");

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.of(statusCode, errorCode, reason);
    }
}
