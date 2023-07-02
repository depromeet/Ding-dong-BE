package com.dingdong.domain.domains.community.repository;


import com.dingdong.domain.domains.community.domain.entity.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
