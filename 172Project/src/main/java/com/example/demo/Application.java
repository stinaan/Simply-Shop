package com.example.demo;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Item;
import com.example.demo.mysqlcommands.ModifyDB;

import com.example.demo.controllers.*;
import com.example.demo.*;


@SpringBootApplication
@EnableConfigurationProperties({
	
})
@RestController
public class Application {
	//Print message when application is run on docker; go help from https://www.youtube.com/watch?v=e3YERpG2rMs at 3:17
	@GetMapping("/docker")
	public String getMessage()
	{
		return "If you see this, docker worked";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		//FOR TESTING PURPOSES ONLY
		ModifyDB test = new ModifyDB();
		
		Item item = new Item("Car","Vehicle",2.00,2,"drive it!",1,null);
		
		System.out.println(item.getDescription());
		//Item item = new Item("Cheese","Dairy","3.99","8","eat it","7");
		
		//test.insert(item);
		
		//test.increaseQuantity(item);
		
		//test.editItem("Cheese","Dairy","4.99","8","eat it",1);

		
		
		try {
			test.viewTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
