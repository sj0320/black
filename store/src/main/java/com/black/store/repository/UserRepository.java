package com.black.store.repository;

import com.black.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);
    boolean existsByNickName(String nickname);
    Optional<User>findByUsername(String username);

}
