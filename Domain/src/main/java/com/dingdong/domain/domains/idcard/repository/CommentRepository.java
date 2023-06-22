package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository
        extends JpaRepository<Comment, Long>, CommentRepositoryExtension {

    List<Comment> findAllByIdCardId(Long idCardId);
}
