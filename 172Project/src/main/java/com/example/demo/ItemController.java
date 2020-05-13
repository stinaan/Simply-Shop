package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.demo.model.Item;

@RestController
public class ItemController {

	// MySQL credentials, got help from https://www.youtube.com/watch?v=_oEOH23OYYQ
	// at 14:21
	private String dburl = new String(
			"jdbc:mysql://localhost:3306/userdb");
	private String dbuname = new String("root");
	private String dbpassword = new String("password");
	private String dbdriver = new String("com.mysql.jdbc.Driver");

	// Load driver from MySQL database, got help from
	// https://www.youtube.com/watch?v=_oEOH23OYYQ at 14:21
	public void loadDriver(String dbDriver) {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Load driver from MySQL database, got help from
	// https://www.youtube.com/watch?v=_oEOH23OYYQ at 16:39
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
	
	//Convert file for S3 conversion, code from https://medium.com/oril/uploading-files-to-aws-s3-bucket-using-spring-boot-483fcb6f8646
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}

//~~~~~~S3 FUNCTIONS~~~~~~//
	AWSCredentials credentials = new BasicAWSCredentials("[This was previously an AWS key]",
			"[This was previously an AWS key]");
	AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
			.withRegion(Regions.US_WEST_1).build();
	String bucketName = "cmpe172project";

	String imageBaseURI = "[this was previously an s3 url]";

	@RequestMapping(value = "/api/testbucket")
	public String testBucket() {

		if (s3client.doesBucketExist(bucketName)) {
			return "bucket:" + bucketName + " exists!";
		}
		return "no bucket found";
	}

	// test upload
	// @RequestMapping(value = "/api/testbucket/upload") -readd this to test
	// manually
	public String uploadImageToS3(String imageName, String path) {

		if (s3client.doesBucketExist(bucketName)) {
			/*
			 * Check if bucket exists
			 */
			PutObjectResult por = s3client.putObject(bucketName, "images/" + imageName, new File(path));

			return por.toString();
		}
		return "upload failed";
	}

	// test delete
	// @RequestMapping(value = "/api/testbucket/delete/{imageID}")
	public String deleteImageFromS3(String imageID) {

		if (s3client.doesBucketExist(bucketName)) {

			s3client.deleteObject(bucketName, "images/" + imageID + "");

			return "delete success";
		}
		return "upload failed";
	}

	// test image retrieving
	 //@RequestMapping(value = "/api/testbucket/images/{imageID}")
	public String getImageFromS3(String imageID) {//@PathVariable("imageID") String imageID) {
		String photoID = "";

		if (s3client.doesBucketExist(bucketName)) {

			S3Object object = s3client.getObject(bucketName, "images/" + imageID + "");
			if (object.getKey() != null) {
				return "[this was previously an S3 link for a bucket]" + imageID;

			}
			return imageID + " imageID not found in bucket";

		}
		return "bucket does not exist!";
	}

	// ~~~~~~ITEMS FUNCTIONS~~~~~~//
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(value = "/api/items", produces = "application/json; charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Item createItem(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "category", required = true) String category,
			@RequestParam(value = "price", required = true) double price,
			@RequestParam(value = "quantity", required = true) int quantity,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "image", required = true) MultipartFile imageFile) throws Exception {
		
		Item item = new Item();
		item.setName(name);
		item.setCategory(category);
		item.setPrice(price);
		item.setQuantity(quantity);
		item.setDescription(description);

		loadDriver(dbdriver);

		Connection con = getConnection();
		int itemID = 0;

		java.sql.Statement stmt = null;

		//
		/*
		 * convert imageFile into a File (unless don't need to) should be able to
		 * retrieve both the file name AND the path (user/image/photo.png, etc) call
		 * testImageUpload(String imageName, String path) to upload to s3 then add
		 * imageName to rds (s3 key is formatted "/images/photo.png" but should only
		 * save "photo.png" to rds, don't save "/images" or wont work for the other
		 * function
		 * 
		 */
		
		File file = convertMultiPartToFile(imageFile);
		String path = file.getPath();
		String fileName = file.getName();
		uploadImageToS3(fileName, path);
		item.setImage(fileName);
		item.setImageURL(imageBaseURI+fileName);

		String query = "insert into userdb.item (name, category, price, quantity, description, imageID) values (\"" + item.getName() + "\",\"" + item.getCategory() + "\",\"" + item.getPrice().toString() + "\",\"" + item.getQuantity().toString() + "\",\"" + item.getDescription() + "\",\"" + fileName + "\") ";
		ModelMap model = new ModelMap();
		try {
			stmt = con.createStatement();
			boolean rs = stmt.execute(query);
			return item;

		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return null;
	}
	@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/edit/{itemID}", produces = "application/json; charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Item editItem( 
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "category", required = true) String category,
			@RequestParam(value = "price", required = true) double price,
			@RequestParam(value = "quantity", required = true) int quantity,
			@RequestParam(value = "description", required = true) String description,
			@PathVariable("itemID") int itemID, 
			@RequestParam(value = "image", required = true) MultipartFile imageFile)
			throws Exception {
		Item item = getItem(itemID);
		item.setName(name);
		item.setCategory(category);
		item.setPrice(price);
		item.setQuantity(quantity);
		item.setDescription(description);
		
		// item.setImage(imageID);
		
		File file = convertMultiPartToFile(imageFile);
		String path = file.getPath();
		String fileName = file.getName();
		uploadImageToS3(fileName, path);
		
		item.setImage(fileName);

		loadDriver(dbdriver);

		Connection con = getConnection();

		java.sql.Statement stmt = null;
		String query = "UPDATE userdb.item SET name = \""+item.getName()+"\", category = \""+item.getCategory()+"\", description = \""+item.getDescription()+"\", price = \""+item.getPrice().toString()+"\", quantity = \""+item.getQuantity().toString()+"\",imageID = \""+fileName+"\" WHERE id = "+itemID;

		try {
			stmt = con.createStatement();
			int rs = stmt.executeUpdate(query);
			String query1 = "select * from userdb.item where name = '" + item.getName() + "' && price = " + item.getPrice();

			ModelMap model = new ModelMap();
			return item;
			
		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return null;
	}

	//gets item from rds
@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "/api/items/{itemID}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Item getItem(@PathVariable("itemID") int itemID) throws SQLException {

		loadDriver(dbdriver);

		Connection con = getConnection();

		java.sql.Statement stmt = null;
		String query = "select * from userdb.item where id = " + itemID + " ";

		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String description = rs.getString("description");
				String theName = rs.getString("name");
				Double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				String imageID = rs.getString("imageID");

				String result = new String(theName + "\t" + category + "\t" + price + "\t" + quantity + "\t"
						+ description + "\t" + id + "\t" + imageID);

				Item item = new Item();
				item.setId(id);
				item.setCategory(category);
				item.setDescription(description);
				item.setName(theName);
				item.setPrice(price);
				item.setQuantity(quantity);
				String imageURL = getImageFromS3(imageID);
				item.setImageURL(imageURL);
				item.setImage(imageID);

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

	// finds all items in repo
	@CrossOrigin(origins = "http://localhost:3000")
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
				String imageID = rs.getString("imageID");

				String result = new String(
						theName + "\t" + category + "\t" + price + "\t" + quantity + "\t" + description + "\t" + id);

				Item item = new Item();
				item.setId(id);
				item.setCategory(category);
				item.setDescription(description);
				item.setName(theName);
				item.setPrice(price);
				item.setQuantity(quantity);
				String imageURL = imageBaseURI + imageID;
				item.setImageURL(imageURL);
				item.setImage(imageID);
				allItems.add(item);
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return allItems;
	}

	// deletes a user in repo
	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping(value = "/api/items/{itemID}")
	@ResponseStatus(HttpStatus.OK)
	public boolean removeItem(@PathVariable("itemID") int itemID) throws SQLException {

		loadDriver(dbdriver);

		Connection con = getConnection();

		Item item = getItem(itemID);
		String imageID = item.getImage();

		java.sql.Statement stmt = null;
		String query = "delete from userdb.item where id = " + itemID + " ";

		try {
			stmt = con.createStatement();
			boolean rs = stmt.execute(query);

			if (rs == true) { //the deletion from rds works
			deleteImageFromS3(imageID); // calls function to delete from S3
			}

			return rs;
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
