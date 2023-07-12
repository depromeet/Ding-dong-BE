package com.dingdong.domain.domains.idcard.exception;

import static com.dingdong.core.consts.StaticVal.BAD_REQUEST;
import static com.dingdong.core.consts.StaticVal.FORBIDDEN;
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
public enum IdCardErrorCode implements BaseErrorCode {
    /** 주민증 Aggregate 관련 에러 코드 */
    @ExplainError("존재하지 않는 사용자의 주민증을 조회할 때 발생하는 오류입니다.")
    NOT_FOUND_ID_CARD(NOT_FOUND, "IdCard-404-1", "존재하지 않는 주민증입니다."),
    @ExplainError("이미 주민증을 등록한 사용자가 주민증 생성을 시도할 때 발생하는 오류입니다.")
    ALREADY_EXIST_ID_CARD(BAD_REQUEST, "IdCard-400-1", "이미 해당 커뮤니티에 주민증을 등록했습니다."),
    NOT_FOUND_COMMENT(NOT_FOUND, "Comment-404-1", "존재하지 않는 댓글입니다."),
    NOT_VALID_ID_CARD_COMMENT(BAD_REQUEST, "IdCard-400-2", "해당 주민증의 댓글이 아닙니다."),
    ALREADY_EXIST_COMMENT_LIKE(BAD_REQUEST, "CommentLike-400-1", "이미 댓글에 좋아요를 눌렀습니다."),
    ALREADY_EXIST_COMMENT_REPLY_LIKE(BAD_REQUEST, "CommentReplyLike-400-1", "이미 대댓글에 좋아요를 눌렀습니다."),
    NOT_FOUND_COMMENT_LIKE(NOT_FOUND, "CommentLike-404-1", "존재하지 않는 댓글 좋아요입니다."),
    NOT_FOUND_COMMENT_REPLY(NOT_FOUND, "CommentReply-404-1", "존재하지 않는 대댓글입니다."),
    NOT_VALID_COMMENT_USER(BAD_REQUEST, "Comment-400-1", "해당 유저가 작성한 댓글이 아닙니다."),
    NOT_VALID_COMMENT_REPLY_USER(BAD_REQUEST, "CommentReply-400-2", "해당 유저가 작성한 대댓글이 아닙니다."),
    NOT_VALID_COMMENT_LIKE_USER(BAD_REQUEST, "CommentLike-400-2", "해당 유저가 누른 댓글 좋아요가 아닙니다."),
    NOT_VALID_COMMENT_REPLY_LIKE_USER(
            BAD_REQUEST, "CommentReplyLike-400-2", "해당 유저가 누른 대댓글 좋아요가 아닙니다."),
    NOT_VALID_COMMENT_REPLY(BAD_REQUEST, "Comment-400-2", "해당 댓글의 대댓글이 아닙니다."),
    NOT_FOUND_COMMENT_REPLY_LIKE(NOT_FOUND, "CommentReplyLike-404-1", "존재하지 않는 대댓글 좋아요입니다."),
    NOT_EXIST_ID_CARD_IN_COMMUNITY(
            FORBIDDEN, "IdCard-403-1", "행성에 주민증을 만들지 않은 유저는 댓글, 좋아요를 등록 할 수 있는 권한이 없습니다."),
    NOT_VALID_ID_CARD_USER(BAD_REQUEST, "IdCard-400-4", "해당 유저가 작성한 주민증이 아닙니다.");

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
