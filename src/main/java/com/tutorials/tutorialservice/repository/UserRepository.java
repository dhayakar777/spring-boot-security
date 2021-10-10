package com.tutorials.tutorialservice.repository;

import com.tutorials.tutorialservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, PagingAndSortingRepository<User, String> {

    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
   // Optional<User>  findByUserNameOrEmail(String userNameOrEmail);
}
