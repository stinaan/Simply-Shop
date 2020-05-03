package com.example.demo;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Item;
import com.example.demo.model.ItemImage;
import com.example.repository.ItemRepository;
import com.example.service.ItemImageService;




@RestController
public class ItemController {
  
  @Autowired
  private ItemRepository itemRepo;
  @Autowired
private ItemImageService imageService;
  
  @Autowired
  private JdbcTemplate temp;
  
  
  @PostMapping(value = "/api/items", produces = "application/json; charset=UTF-8")
  @ResponseBody
  public Item createItem(
		  				 @RequestParam(value="name", required=true) String name,
                         @RequestParam(value="category", required=true) String category,
                         @RequestParam(value="price", required=true) double price,
                         @RequestParam(value="quantity", required=true) int quantity,
                         @RequestParam(value="description", required=true) String description,
                         @RequestParam(value="id", required=true) Integer itemID,
                         @RequestParam(value="image", required=true) MultipartFile image) throws Exception {
    ItemImage itemImage = imageService.addImage(image); 
	Item item = new Item(name,  category,  price,  quantity,  description,  itemID, itemImage);
    itemRepo.save(item);
    return item; 
  }
  
  //edit item in repo
  @PostMapping(value = "/api/edit/{itemID}", produces = "application/json; charset=UTF-8")
  @ResponseBody
  public Item editItem(
		  				 @RequestParam(value="name", required=true) String name,
                         @RequestParam(value="category", required=true) String category,
                         @RequestParam(value="price", required=true) double price,
                         @RequestParam(value="quantity", required=true) int quantity,
                         @RequestParam(value="description", required=true) String description,
                         @PathVariable("itemID") int itemID,
                         @RequestParam(value="image", required=true) MultipartFile image) throws Exception {
    Item item = getItem(itemID);
    item.setName(name);
    item.setCategory(category);
    item.setPrice(price);
    item.setQuantity(quantity);
    item.setDescription(description);
    item.setImage((ItemImage) image);
    
    loadDriver(dbdriver);

	Connection con = getConnection();

	java.sql.Statement stmt = null;
	String query = "UPDATE userdb.item SET name = "+name+", category = "+category+", description = "+description+", price = "+price+", quantity = "+quantity+" WHERE id = "+itemID+" ";

	try {
		stmt = con.createStatement();
		ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
		
	} catch (SQLException e) {
		System.out.println("SQL Exception");
	} finally {
		if (stmt != null) {
			stmt.close();
		}
	}
	return null;
  }
  
  

	//MySQL credentials, got help from https://www.youtube.com/watch?v=_oEOH23OYYQ at 14:21
	private String dburl = new String("jdbc:mysql://cmpe172database.c2yryz8m0mvy.us-east-1.rds.amazonaws.com:3306/userdb");
	private String dbuname = new String("root");
	private String dbpassword = new String("thomas172");
	private String dbdriver = new String("com.mysql.jdbc.Driver");

	//Load driver from MySQL database, got help from https://www.youtube.com/watch?v=_oEOH23OYYQ at 14:21
	public void loadDriver(String dbDriver) {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Load driver from MySQL database, got help from https://www.youtube.com/watch?v=_oEOH23OYYQ at 16:39
	public Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(dburl, dbuname, dbpassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;
	}
    
    //finds the user in repo
    @GetMapping(value = "/api/items/{itemID}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Item getItem(@PathVariable("itemID") int itemID) throws SQLException {
      
    	loadDriver(dbdriver);

		Connection con = getConnection();

		java.sql.Statement stmt = null;
		String query = "select * from userdb.item where id = "+itemID+" ";

		try {
			stmt = con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String description = rs.getString("description");
				String theName = rs.getString("name");
				Double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				
				String result = new String(
						theName + "\t" + category + "\t" + price + "\t" + quantity + "\t" + description + "\t" + id);

				Item item = new Item();
				item.setId(id);
				item.setCategory(category);
				item.setDescription(description);
				item.setName(theName);
				item.setPrice(price);
				item.setQuantity(quantity);
				return item;
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return null;
  }
    
    //finds all items in repo
    @GetMapping(value = "/api/items", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Item> getItems() throws SQLException {
    	List<Item> allItems = new ArrayList<Item>();
    	loadDriver(dbdriver);

		Connection con = getConnection();

		java.sql.Statement stmt = null;
		String query = "select * from userdb.item";

		try {
			stmt = con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String description = rs.getString("description");
				String theName = rs.getString("name");
				Double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				
				String result = new String(
						theName + "\t" + category + "\t" + price + "\t" + quantity + "\t" + description + "\t" + id);

				Item item = new Item();
				item.setId(id);
				item.setCategory(category);
				item.setDescription(description);
				item.setName(theName);
				item.setPrice(price);
				item.setQuantity(quantity);
				//return item;
				allItems.add(item);
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		//return null;      
     // return (List<Item>) itemRepo.findAll();
		return allItems;
    }
    
	//Use this to test the connection
	@RequestMapping("/api/help")
	public String sayHello() {
		return "Helppppppp";
	}
    
    
    
    //deletes a user in repo
    @DeleteMapping(value = "/api/items/{itemID}")
    public boolean removeItem(@PathVariable("itemID") int itemID) throws SQLException {
      
        
      	loadDriver(dbdriver);

  		Connection con = getConnection();

  		java.sql.Statement stmt = null;
  		String query = "delete from item where id = "+itemID+" ";

  		try {
  			stmt = con.createStatement();
  			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
  			return true;
  		} catch (SQLException e) {
  			System.out.println("SQL Exception");
  		} finally {
  			if (stmt != null) {
  				stmt.close();
  			}
  		}
  		return false;
    }
    
    
    //testing request body
    @PostMapping("/api/testtest")
    public ResponseEntity<Item> create(@RequestBody Item item) throws URISyntaxException{
    	Item newItem = new Item();
    	if (newItem == null) {
    		return ResponseEntity.notFound().build();
    	} else {
    		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
    				.path("{id}")
    				.buildAndExpand(newItem.getId())
    				.toUri();
    		return ResponseEntity.created(uri)
					.body(newItem);
    	}
    			
    	
    }
    
    @GetMapping("/api/testtest1/{id}")
    public ResponseEntity<Item> read(@PathVariable("id") int id) {
    	Item item = itemRepo.getOne(id);
    	if (item == null) {
    		return ResponseEntity.notFound().build();
    	} else {
    		return ResponseEntity.ok(item);
    	}
    	
    }
}
