package com.alisimsek.HumorousBlog.repository;

import com.alisimsek.HumorousBlog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMail(String mail);

    User findByactivationToken(String token);
}
