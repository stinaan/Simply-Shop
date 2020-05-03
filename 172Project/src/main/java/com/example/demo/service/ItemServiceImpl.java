package com.example.demo.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DAO.ItemDAO;
import com.example.demo.model.Item;


	@Service
	public class ItemServiceImpl implements ItemService {
		 @Autowired
		 private ItemDAO itemDAO;

		 @Transactional
		 @Override
		public List<Item> get() {
			// TODO Auto-generated method stub
			return itemDAO.get();
		}

		 @Transactional
		 @Override
		public Item get(int id) {
			// TODO Auto-generated method stub
return itemDAO.get(id);
}
		 @Transactional
		@Override
		public void save(Item item) {
			// TODO Auto-generated method stub
			 itemDAO.save(item);
			
		}
		 @Transactional
		@Override
		public void delete(int id) {
			// TODO Auto-generated method stub
			 itemDAO.delete(id);
			
		}
		
		
}
