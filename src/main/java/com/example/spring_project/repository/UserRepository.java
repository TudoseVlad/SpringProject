package com.example.spring_project.repository;

import org.springframework.stereotype.Repository;
import com.example.spring_project.entity.User;


import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}