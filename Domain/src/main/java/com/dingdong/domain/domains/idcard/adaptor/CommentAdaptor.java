package com.dingdong.domain.domains.idcard.adaptor;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_COMMENT;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_COMMENT_LIKE;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_COMMENT_REPLY;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_COMMENT_REPLY_LIKE;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import com.dingdong.domain.domains.idcard.domain.entity.CommentLike;
import com.dingdong.domain.domains.idcard.domain.entity.CommentReply;
import com.dingdong.domain.domains.idcard.domain.entity.CommentReplyLike;
import com.dingdong.domain.domains.idcard.domain.vo.CommentVo;
import com.dingdong.domain.domains.idcard.repository.CommentRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@Adaptor
@RequiredArgsConstructor
public class CommentAdaptor {

    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
    }

    public Slice<CommentVo> findCommentsByIdCard(Long idCardId, Pageable pageable) {
        return commentRepository.findCommentsByIdCardId(idCardId, pageable);
    }

    public CommentReply findCommentReply(Comment comment, Long commentReplyId) {
        List<CommentReply> replies = comment.getReplies();

        return replies.stream()
                .filter(commentReply -> Objects.equals(commentReply.getId(), commentReplyId))
                .findFirst()
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT_REPLY));
    }

    public CommentLike findCommentLike(Comment comment, Long commentLikeId) {
        List<CommentLike> likes = comment.getLikes();

        return likes.stream()
                .filter(commentLike -> Objects.equals(commentLike.getId(), commentLikeId))
                .findFirst()
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT_LIKE));
    }

    public CommentReplyLike findCommentReplyLike(
            CommentReply commentReply, Long commentReplyLikeId) {
        List<CommentReplyLike> replyLikes = commentReply.getReplyLikes();

        return replyLikes.stream()
                .filter(
                        commentReplyLike ->
                                Objects.equals(commentReplyLike.getId(), commentReplyLikeId))
                .findFirst()
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT_REPLY_LIKE));
    }

    public List<Comment> findAllByIdCard(Long idCardId) {
        return commentRepository.findAllByIdCardId(idCardId);
    }
}
