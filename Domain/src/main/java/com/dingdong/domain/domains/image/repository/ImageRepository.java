package com.dingdong.domain.domains.image.repository;


import com.dingdong.domain.domains.image.domain.entity.DeleteImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<DeleteImage, Long> {}
