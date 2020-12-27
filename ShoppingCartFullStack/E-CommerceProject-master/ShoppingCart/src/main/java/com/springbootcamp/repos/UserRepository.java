package com.springbootcamp.repos;

import com.springbootcamp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByPassword(String password);

    Optional<User> findByFileName(String fileId);
}
