package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.model.CommentReplyVo;
import com.dingdong.domain.domains.idcard.domain.model.CommentVo;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentRepositoryExtension {

    Slice<CommentVo> findCommentsByIdCardId(Long idCardId, Long communityId, Pageable pageable);

    List<CommentVo> findCommentsByParentCommentId(Long communityId, Long parentCommentId);

    List<CommentReplyVo> findReplies(Long commentId, Long communityId);
}
