package com.dingdong.domain.domains.community.repository;


import com.dingdong.domain.domains.community.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {}
