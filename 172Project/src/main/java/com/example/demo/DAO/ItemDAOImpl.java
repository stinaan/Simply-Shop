package com.example.demo.DAO;
import java.util.List;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Item;

import javax.persistence.EntityManager;
	@Repository
	public class ItemDAOImpl implements ItemDAO {
	@Autowired
	 private EntityManager entityManager;
	@Override
	 public List<Item> get() {
	Session currSession = entityManager.unwrap(Session.class);
	  Query<Item> query = currSession.createQuery("from Items", Item.class);
	  List<Item> list = query.getResultList();
	  return list;
	}
	@Override
	 public Item get(int id) {
	  Session currSession = entityManager.unwrap(Session.class);
	  Item emp = currSession.get(Item.class, id);
	  return emp;
	 }
	@Override
	 public void save(Item item) {
	  
	  Session currSession = entityManager.unwrap(Session.class);
	  currSession.saveOrUpdate(item);
	}
	@Override
	 public void delete(int id) {
	  Session currSession = entityManager.unwrap(Session.class);
	  Item emp = currSession.get(Item.class, id);
	  currSession.delete(emp);
	}
	}