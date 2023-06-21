package com.dingdong.domain.domains.idcard.validator;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.ALREADY_EXIST_COMMENT_LIKE;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.ALREADY_EXIST_COMMENT_REPLY_LIKE;

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
}
