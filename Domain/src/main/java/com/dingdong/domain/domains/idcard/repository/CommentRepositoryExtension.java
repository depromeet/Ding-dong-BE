package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.model.CommentVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentRepositoryExtension {

    Slice<CommentVo> findCommentsByIdCardId(Long idCardId, Pageable pageable);
}
