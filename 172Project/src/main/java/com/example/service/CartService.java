package com.example.service;

import java.util.Map;

import com.example.demo.model.Item;

public interface CartService {
	

    void addProduct(Item item);

    void removeProduct(Item item);
    
    void clearProducts();

    Map<Item, Integer> getCartItem();

    void checkout();

    double getTotal();

}
