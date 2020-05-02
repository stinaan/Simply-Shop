package com.example.demo.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Item;

import java.util.Optional;

public interface ItemService {

    Optional<Item> findById(int itemID);

    Page<Item> findAllProductsPageable(Pageable pageable);

}