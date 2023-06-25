package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.vo.CommentVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentRepositoryExtension {

    Slice<CommentVo> findCommentsByIdCardId(Long idCardId, Pageable pageable);
}
