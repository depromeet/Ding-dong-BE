package com.dingdong.domain.domains.user.domain;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    User findByEmail(String email);

    User findByNickname(String nickname);

    boolean existsByNickname(String nickName);

    boolean existsByEmail(String email);
}
