package com.dingdong.domain.domains.idcard.validator;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.ALREADY_EXIST_COMMENT_LIKE;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.ALREADY_EXIST_COMMENT_REPLY_LIKE;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_VALID_COMMENT_LIKE_USER;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_VALID_COMMENT_REPLY;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_VALID_COMMENT_REPLY_LIKE_USER;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_VALID_COMMENT_REPLY_USER;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_VALID_COMMENT_USER;

import com.dingdong.core.annotation.Validator;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.idcard.adaptor.CommentAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import com.dingdong.domain.domains.idcard.domain.entity.CommentLike;
import com.dingdong.domain.domains.idcard.domain.entity.CommentReply;
import com.dingdong.domain.domains.idcard.domain.entity.CommentReplyLike;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@Validator
@RequiredArgsConstructor
public class CommentValidator {

    private final CommentAdaptor commentAdaptor;

    public void isExistCommentLike(Comment comment, Long userId) {
        List<CommentLike> likes = comment.getLikes();

        boolean isExist =
                likes.stream()
                        .anyMatch(commentLike -> Objects.equals(commentLike.getUserId(), userId));

        if (isExist) {
            throw new BaseException(ALREADY_EXIST_COMMENT_LIKE);
        }
    }

    public void isExistCommentReplyLike(CommentReply commentReply, Long userId) {
        List<CommentReplyLike> replyLikes = commentReply.getReplyLikes();

        boolean isExist =
                replyLikes.stream()
                        .anyMatch(commentLike -> Objects.equals(commentLike.getUserId(), userId));

        if (isExist) {
            throw new BaseException(ALREADY_EXIST_COMMENT_REPLY_LIKE);
        }
    }

    public void isValidCommentReply(Comment comment, CommentReply commentReply) {
        if (!Objects.equals(comment.getId(), commentReply.getId())) {
            throw new BaseException(NOT_VALID_COMMENT_REPLY);
        }
    }

    public void isValidCommentUser(Comment comment, Long userId) {
        if (!Objects.equals(comment.getUserId(), userId)) {
            throw new BaseException(NOT_VALID_COMMENT_USER);
        }
    }

    public void isValidCommentReplyUser(CommentReply commentReply, Long userId) {
        if (!Objects.equals(commentReply.getUserId(), userId)) {
            throw new BaseException(NOT_VALID_COMMENT_REPLY_USER);
        }
    }

    public void isValidCommentLikeUser(CommentLike commentLike, Long userId) {
        if (!Objects.equals(commentLike.getUserId(), userId)) {
            throw new BaseException(NOT_VALID_COMMENT_LIKE_USER);
        }
    }

    public void isValidCommentReplyLikeUser(CommentReplyLike commentReplyLike, Long userId) {
        if (!Objects.equals(commentReplyLike.getUserId(), userId)) {
            throw new BaseException(NOT_VALID_COMMENT_REPLY_LIKE_USER);
        }
    }
}
