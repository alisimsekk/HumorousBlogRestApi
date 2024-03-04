package com.alisimsek.HumorousBlog.repository;

import com.alisimsek.HumorousBlog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMail(String mail);

    User findByactivationToken(String token);

   /* Projection
    @Query(value = "select u from User u")
    Page<UserProjection> getAllUserRecords(Pageable page);*/

    Page<User> findByIdNot (Long id, Pageable page);

}
