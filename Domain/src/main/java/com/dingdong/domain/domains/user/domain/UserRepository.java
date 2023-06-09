package com.dingdong.domain.domains.user.domain;


import com.dingdong.domain.common.consts.Status;
import com.dingdong.domain.domains.user.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByIdAndIsDeleted(Long id, Status isDeleted);

    Optional<User> findByEmail(String email);
}
