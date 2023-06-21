package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentRepositoryExtension {

    Slice<Comment> findCommentsByIdCardId(Long idCardId, Pageable pageable);
}
