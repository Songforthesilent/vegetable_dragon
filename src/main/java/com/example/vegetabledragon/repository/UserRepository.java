package com.example.vegetabledragon.repository;

import com.example.vegetabledragon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByAnonymousName(String anonymousName);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
