package com.dingdong.domain.domains.idcard.repository;


import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository
        extends JpaRepository<Comment, Long>, CommentRepositoryExtension {}
