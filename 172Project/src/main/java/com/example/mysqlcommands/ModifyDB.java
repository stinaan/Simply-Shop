package com.example.mysqlcommands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.model.Item;
//import com.mysql.cj.xdevapi.Statement;

/**
 * Modify the database
 * @author Richard Pham
 *Edited by Christina Nguyen
 *	took out Integer.parse bc quantity is now an int
 */
public class ModifyDB {

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

	
	//Create table for items, got help from https://www.youtube.com/watch?v=_oEOH23OYYQ at 12:15
	public void createTable() throws SQLException {

		loadDriver(dbdriver);

		Connection con = getConnection();

		java.sql.Statement stmt = null;
		String query = "CREATE TABLE IF NOT EXISTS item ( theName VARCHAR(50), category VARCHAR(50), price DECIMAL(10,2), quantity INT, theDescription VARCHAR(150), id INT AUTO_INCREMENT, PRIMARY KEY (id) )";

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.executeUpdate();

			System.out.println("Success");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//View table for items, got help from https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html and https://www.youtube.com/watch?v=_oEOH23OYYQ at 13:02 and 12:15
	public void viewTable(HttpServletResponse response) throws SQLException {

		loadDriver(dbdriver);

		Connection con = getConnection();

		java.sql.Statement stmt = null;
		String query = "select theName, category, price, " + "quantity, theDescription, id " + "from userdb.item";

		try {
			stmt = con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			while (rs.next()) {
				String theName = rs.getString("theName");
				String category = rs.getString("category");
				Double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				String description = rs.getString("theDescription");
				int id = rs.getInt("id");
				String result = new String(
						theName + "\t" + category + "\t" + price + "\t" + quantity + "\t" + description + "\t" + id);

				try {
					response.getWriter().print(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	//View table for items, got help from https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html and https://www.youtube.com/watch?v=_oEOH23OYYQ at 12:15
	public void viewTable() throws SQLException {

		loadDriver(dbdriver);

		Connection con = getConnection();

		java.sql.Statement stmt = null;

		String query = "select theName, category, price, " + "quantity, theDescription, id " + "from userdb.item";

		try {
			stmt = con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			while (rs.next()) {
				String theName = rs.getString("theName");
				String category = rs.getString("category");
				Double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				String description = rs.getString("theDescription");
				int id = rs.getInt("id");
				String result = new String(
						theName + "\t" + category + "\t" + price + "\t" + quantity + "\t" + description + "\t" + id);

				System.out.println(result);
			}
		} catch (SQLException e) {
			System.out.println("SQL exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	// View table by name, got help from https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html and https://www.youtube.com/watch?v=_oEOH23OYYQ at 12:15
	public void viewTableByName(String name) throws SQLException {

		loadDriver(dbdriver);

		Connection con = getConnection();

		java.sql.Statement stmt = null;

		String query = "select theName, category, price, " + "quantity, theDescription, id "
				+ "from userdb.item where theName = '" + name + "'";

		try {
			stmt = con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			while (rs.next()) {
				String theName = rs.getString("theName");
				String category = rs.getString("category");
				Double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				String description = rs.getString("theDescription");
				int id = rs.getInt("id");
				String result = new String(
						theName + "\t" + category + "\t" + price + "\t" + quantity + "\t" + description + "\t" + id);

				System.out.println(result);
			}
		} catch (SQLException e) {
			System.out.println("SQL exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	// View table by category, got help from https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html and https://www.youtube.com/watch?v=_oEOH23OYYQ at 12:15
	public void viewTableByCategory(String theCategory) throws SQLException {

		loadDriver(dbdriver);

		Connection con = getConnection();

		java.sql.Statement stmt = null;

		String query = "select theName, category, price, " + "quantity, theDescription, id "
				+ "from userdb.item where category = '" + theCategory + "'";

		try {
			stmt = con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			while (rs.next()) {
				String theName = rs.getString("theName");
				String category = rs.getString("category");
				Double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				String description = rs.getString("theDescription");
				int id = rs.getInt("id");
				String result = new String(
						theName + "\t" + category + "\t" + price + "\t" + quantity + "\t" + description + "\t" + id);

				System.out.println(result);
			}
		} catch (SQLException e) {
			System.out.println("SQL exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	// Edit item, got help from https://www.youtube.com/watch?v=_oEOH23OYYQ at 12:15
	public void editItem( String name, String category, String price, String quantity, String description, int id) {

		loadDriver(dbdriver);

		Connection con = getConnection();
		String sql = "UPDATE item SET theName = ?, "+
		"category = ?, price = ?, quantity = ?, "+
		"theDescription = ? WHERE id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setString(3, price);
			ps.setString(4, quantity);
			ps.setString(5, description);
			ps.setInt(6,id);
			ps.executeUpdate();
			System.out.println("Success");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Delete entry from table, got help from https://www.youtube.com/watch?v=_oEOH23OYYQ at 12:15
	public void deleteEntry(int id) throws SQLException {

		loadDriver(dbdriver);

		Connection con = getConnection();
		String sql = "DELETE FROM item " + "WHERE id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

			System.out.println("Success");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Insert an item to database, got help from https://www.youtube.com/watch?v=_oEOH23OYYQ at 12:15
	public String insert(Item item) {
		loadDriver(dbdriver);

		Connection con = getConnection();
		String result = "Item inserted";
		String sql = "insert into userdb.item(theName, category, price, quantity, theDescription) values (?,?,?,?,?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, item.getName());
			ps.setString(2, item.getCategory());
			ps.setDouble(3, item.getPrice()); //
			ps.setInt(4, item.getQuantity()); //
			ps.setString(5, item.getDescription());
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	// Reduce quantity of item, got help from https://www.youtube.com/watch?v=_oEOH23OYYQ at 12:15
	public void reduceQuantity(Item item) {

		int q = 0;

		if (item.getCategory().length() < 0) {
			return;
		}

		try {
			q = (item.getQuantity());
			if (q <= 0 //|| item.getQuantity().contains("-") --do we need this still?
					|| (item.getQuantity() == 0 && item.getCategory().length() == 1)) {
				System.out.println("There is no more " + item.getName() + " in stock!");
				return;
			}
		} catch (Exception e) {
			System.out.println("Unable to get quantity of item");
			return;
		}

		loadDriver(dbdriver);

		Connection con = getConnection();
		String sql = "UPDATE item " + "SET quantity = ? " + "WHERE id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			q--;
			ps.setInt(1, q);
			ps.setInt(2, (item.getId()));
			ps.executeUpdate();

			System.out.println("Success");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Increase quantity of item, got help from https://www.youtube.com/watch?v=_oEOH23OYYQ at 12:15
	public void increaseQuantity(Item item) {

		int q = 0;

		if (item.getCategory().length() < 0) {
			return;
		}

		try {
			q = (item.getQuantity());
		} catch (Exception e) {
			System.out.println("Unable to get quantity of item");
			return;
		}

		loadDriver(dbdriver);

		Connection con = getConnection();
		String sql = "UPDATE item " + "SET quantity = ? " + "WHERE id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			q++;
			ps.setInt(1, q);
			ps.setInt(2, (item.getId()));
			ps.executeUpdate();

			System.out.println("Success");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
