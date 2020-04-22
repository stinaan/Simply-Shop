package com.example.demo;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.model.Item;
import com.example.mysqlcommands.ModifyDB;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		
		/**
		//FOR TESTING PURPOSES ONLY
		ModifyDB test = new ModifyDB();
		
		Item item = new Item("Car","Vehicle","29999.99","2","drive it!","1");
		//Item item = new Item("Cheese","Dairy","3.99","8","eat it","7");
		
		//test.insert(item);
		
		//test.increaseQuantity(item);
		
		test.editItem("Cheese","Dairy","4.99","8","eat it",1);

		
		
		try {
			test.viewTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		**/
		
	}

}
