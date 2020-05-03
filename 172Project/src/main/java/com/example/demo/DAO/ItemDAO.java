package com.example.demo.DAO;
import java.util.List;

import com.example.demo.model.Item;
public interface ItemDAO {
	 List<Item> get();
	 
	 Item get(int id);
	 
	 void save(Item item);
	 
	 void delete(int id);

}
