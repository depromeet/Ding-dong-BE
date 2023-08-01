package com.dingdong.domain.domains.idcard.adaptor;

import static com.dingdong.domain.common.consts.Status.N;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_COMMENT;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_COMMENT_LIKE;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import com.dingdong.domain.domains.idcard.domain.entity.CommentLike;
import com.dingdong.domain.domains.idcard.domain.model.CommentVo;
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
                .findByIdAndIsDeleted(commentId, N)
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
    }

    public Slice<CommentVo> findCommentsByIdCard(
            Long idCardId, Long communityId, Pageable pageable) {
        return commentRepository.findCommentsByIdCardId(idCardId, communityId, pageable);
    }

    public List<CommentVo> findCommentsByParentCommentId(Long communityId, Long parentCommentId) {
        return commentRepository.findCommentsByParentCommentId(communityId, parentCommentId);
    }

    public List<Comment> findReplies(Long parentCommentId) {
        return commentRepository.findAllByParentCommentIdAndIsDeleted(parentCommentId, N);
    }

    public CommentLike findCommentLikeByUserId(Comment comment, Long currentUserId) {
        List<CommentLike> likes = comment.getLikes();

        return likes.stream()
                .filter(commentLike -> Objects.equals(commentLike.getUserId(), currentUserId))
                .findFirst()
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT_LIKE));
    }

    public List<Comment> findAllByIdCard(Long idCardId) {
        return commentRepository.findAllByIdCardIdAndIsDeleted(idCardId, N);
    }

    public Long findCommentCountByIdCard(Long idCardId) {
        return commentRepository.countByIdCardIdAndIsDeleted(idCardId, N);
    }
}
