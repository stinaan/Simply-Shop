package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.User;
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(@Param("username") String username);
	
}