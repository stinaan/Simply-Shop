package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;
public interface UserRepository extends JpaRepository<User, Integer> {
	
}