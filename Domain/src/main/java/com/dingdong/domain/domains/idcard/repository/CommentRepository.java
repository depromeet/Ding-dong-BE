package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.common.consts.Status;
import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository
        extends JpaRepository<Comment, Long>, CommentRepositoryExtension {

    Optional<Comment> findByIdAndIsDeleted(Long commentId, Status isDeleted);

    List<Comment> findAllByIdCardIdAndIsDeleted(Long idCardId, Status isDeleted);
}
