package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Item;
public interface ItemRepository extends JpaRepository<Item, Integer> {
	
}