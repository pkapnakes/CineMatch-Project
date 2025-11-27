package com.cinematch.project.repositories;

import com.cinematch.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmailAndPassword(String email, String password);
}
