package com.dingdong.domain.domains.idcard.adaptor;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_COMMENT;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import com.dingdong.domain.domains.idcard.repository.CommentRepository;
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

    public Slice<Comment> findCommentsByIdCard(Long idCardId, Pageable pageable) {
        return commentRepository.findCommentsByIdCardId(idCardId, pageable);
    }
}
