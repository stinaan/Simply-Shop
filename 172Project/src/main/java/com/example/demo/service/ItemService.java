package com.example.demo.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

 List<Item> get();
	 
	 Item get(int id);
	 
	 void save(Item item);
	 
	 void delete(int id);

}