package com.example.demo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.repository.UserRepository;




@RestController
public class UserController {
	@Autowired
	  private UserRepository userRepo;
	
	

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
    @PostMapping(value = "/api/login", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public boolean checkLogin(@RequestParam(value="username", required=true) String username,
    		@RequestParam(value="password", required=true) String password) throws SQLException {
      
    	loadDriver(dbdriver);

		Connection con = getConnection();

		java.sql.Statement stmt = null;
		String query = "select * from userdb.user where username = "+username+" && password ="+password;

		try {
			stmt = con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return false;

  }
	
	

	    
	    //finds the user in repo
	    @GetMapping(value = "/api/users/{userID}", produces = "application/json; charset=UTF-8")
	    @ResponseBody
	    public User getCustomer(@PathVariable("userID") int userID) throws SQLException {
	      
	    	loadDriver(dbdriver);

			Connection con = getConnection();

			java.sql.Statement stmt = null;
			String query = "select * from userdb.user where userid = "+userID+" ";

			try {
				stmt = con.createStatement();
				ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
				while (rs.next()) {
					int id = rs.getInt("userid");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String pw = rs.getString("password");
					String userN = rs.getString("username");
					
					String result = new String(
							pw + "\t" + firstName + "\t" + userN + "\t" + lastName + "\t" + id);

					User user = new User();
					user.setID(id);
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setPassword(pw);
					return user;
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
	    
	    //finds all users in repo
	    @GetMapping(value = "/api/users", produces = "application/json; charset=UTF-8")
	    @ResponseBody
	    public List<User> getUsers() throws SQLException {
	    	List<User> allUsers = new ArrayList<User>();
	    	loadDriver(dbdriver);

			Connection con = getConnection();

			java.sql.Statement stmt = null;
			String query = "select * from userdb.user";

			try {
				stmt = con.createStatement();
				ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
				while (rs.next()) {
					int id = rs.getInt("userid");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String pw = rs.getString("password");
					String userN = rs.getString("username");
					
					String result = new String(
							pw + "\t" + firstName + "\t" + userN + "\t" + lastName + "\t" + id);

					User user = new User();
					user.setID(id);
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setPassword(pw);
					allUsers.add(user);
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
			return allUsers;
	    }
	    
	    //deletes a user in repo
	    @DeleteMapping(value = "/api/users/{userID}")
	    public boolean removeCustomer(@PathVariable("userID") int userID) throws SQLException {
	        
	        
	      	loadDriver(dbdriver);

	  		Connection con = getConnection();

	  		java.sql.Statement stmt = null;
	  		String query = "delete from userdb.user where userid = "+userID+" ";

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
  
}
