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

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

	// MySQL credentials, got help from https://www.youtube.com/watch?v=_oEOH23OYYQ
	// at 14:21
	private String dburl = new String(
			"jdbc:mysql://cmpe172database.c2yryz8m0mvy.us-east-1.rds.amazonaws.com:3306/userdb");
	private String dbuname = new String("root");
	private String dbpassword = new String("thomas172");
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
	
	//code from https://medium.com/oril/uploading-files-to-aws-s3-bucket-using-spring-boot-483fcb6f8646
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}

//~~~~~~S3 FUNCTIONS~~~~~~//
	AWSCredentials credentials = new BasicAWSCredentials("AKIAVRMN5GGM6TJ5SKOL",
			"se6DpesQKZq0My2dzgmFLHyxHeJZNuFgcJWXtbzy");
	AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
			.withRegion(Regions.US_WEST_1).build();
	String bucketName = "cmpe172project";

	String imageBaseURI = "https://cmpe172project.s3-us-west-1.amazonaws.com/images/";

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
		/*
		 * @PostMapping("/upload") public String uploadFile(@RequestPart(value = "file")
		 * MultipartFile file) { return this.amazonClient.upload(file); }
		 */

		if (s3client.doesBucketExist(bucketName)) {
			/*
			 * Be careful to set the correct content type in the metadata object before
			 * directly sending a stream. Unlike file uploads, content types from input
			 * streams cannot be automatically determined. If the caller doesn't explicitly
			 * set the content type, it will not be set in Amazon S3. Content length must be
			 * specified before data can be uploaded to Amazon S3. Amazon S3 explicitly
			 * requires that the content length be sent in the request headers before it
			 * will accept any of the data. If the caller doesn't provide the length, the
			 * library must buffer the contents of the input stream in order to calculate
			 * it.
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

		///////// ~~~~~~~~~~~~~~~~~~~~~~~~

		if (s3client.doesBucketExist(bucketName)) {
			// ObjectListing objectListing = s3client.listObjects(bucketName);
			// photoID+="raw imageID:"+imageID+" | ";
			/*
			 * for(S3ObjectSummary os : objectListing.getObjectSummaries()) { //photoID +=
			 * "raw os value:"+os.getKey()+"  |  "; //String checkID = "images/"+imageID;
			 * //photoID += "checkingID:"+checkID+"  |  "; if
			 * (os.getKey().matches("images/"+imageID+"")) { photoID = os.getKey(); //return
			 * photoID+" found!"; }
			 * 
			 * 
			 * }
			 */
			S3Object object = s3client.getObject(bucketName, "images/" + imageID + "");
			if (object.getKey() != null) {
				// return object.getKey();
				//return object.getRedirectLocation();
				return "https://cmpe172project.s3-us-west-1.amazonaws.com/images/" + imageID;

			}
			return imageID + " imageID not found in bucket";

			// GeneratePresignedUrlRequest request = new
			// GeneratePresignedUrlRequest("bucketname", "path/to/image");
			// String url = conn.generatePresignedUrl(request)

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

			// model.addAttribute("attribute", "redirectWithRedirectPrefix");
			return item;
			//return "it works!";

		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		//return query+" does not work";
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
				//String imageURL = imageBaseURI + imageID;
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
				// return item;
				allItems.add(item);
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		// return null;
		// return (List<Item>) itemRepo.findAll();
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
	
	
	

	// ~~~~~~RANDOM TEST FUNCTIONS~~~~~~//

	// Use this to test the connection
	@RequestMapping("/api/help")
	public String sayHello() {
		return "Helppppppp";
	}

	// testing request body
	@PostMapping("/api/testtest")
	public ResponseEntity<Item> create(@RequestBody Item item) throws URISyntaxException {
		Item newItem = new Item();
		if (newItem == null) {
			return ResponseEntity.notFound().build();
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(newItem.getId())
					.toUri();
			return ResponseEntity.created(uri).body(newItem);
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
	
	
	
	@RequestMapping(value = "/api/test/edit/{itemID}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ModelAndView editItemTest(@PathVariable("itemID") int itemID)
			throws Exception {
		Item item = getItem(1);
		item.setName("test2");
		item.setCategory("test2");
		item.setPrice(1.00);
		item.setQuantity(1);
		item.setDescription("test2");
		item.setImage("testPhoto.png");
		// item.setImage(imageID);

		loadDriver(dbdriver);

		Connection con = getConnection();

		java.sql.Statement stmt = null;
		String query = "UPDATE userdb.item SET name = \""+item.getName()+"\", category = \""+item.getCategory()+"\", description = \""+item.getDescription()+"\", price = \""+item.getPrice().toString()+"\", quantity = \""+item.getQuantity().toString()+"\" WHERE id = "+itemID;

		try {
			stmt = con.createStatement();
			int rs = stmt.executeUpdate(query);
			String query1 = "select * from userdb.item where name = '" + item.getName() + "' && price = " + item.getPrice();
			ModelMap model = new ModelMap();
			return new ModelAndView("redirect:/api/items/" + itemID, model);
		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		//return new ModelAndView("redirect:/api/help/", model);
		//return query+": does not work";
		return null;
	}


	

	
	@RequestMapping(value = "/api/test/createitem", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ModelAndView createItemTest() throws Exception {
		// ItemImage itemImage = imageService.addImage(image);
		// Item item = new Item(name, category, price, quantity, description, itemID,
		// itemImage);
		// itemRepo.save(item);
		Item item = getItem(1);
		item.setName("test2");
		item.setCategory("test2");
		item.setPrice(1.00);
		item.setQuantity(1);
		item.setDescription("test2");
		item.setImage("testPhoto.png");
		String fileName = "testPhoto.png";
		
		
		
		
		loadDriver(dbdriver);

		Connection con = getConnection();
		int itemID = 0;

		java.sql.Statement stmt = null;

		// TODO:
		/*
		 * convert imageFile into a File (unless don't need to) should be able to
		 * retrieve both the file name AND the path (user/image/photo.png, etc) call
		 * testImageUpload(String imageName, String path) to upload to s3 then add
		 * imageName to rds (s3 key is formatted "/images/photo.png" but should only
		 * save "photo.png" to rds, don't save "/images" or wont work for the other
		 * function
		 * 
		 */
/*
		File iFile = (File) imageFile;
		String fileName = iFile.getName();
		String path = iFile.getPath();
		*/
		
		/*
		File file = convertMultiPartToFile(imageFile);
		String path = file.getPath();
		String fileName = file.getName();
		uploadImageToS3(fileName, path);*/

		String query = "insert into userdb.item (name, category, price, quantity, description, imageID) values (\"" + item.getName() + "\",\"" + item.getCategory() + "\",\"" + item.getPrice().toString() + "\",\"" + item.getQuantity().toString() + "\",\"" + item.getDescription() + "\",\"" + fileName + "\") ";
		ModelMap model = new ModelMap();
		try {
			stmt = con.createStatement();
			boolean rs = stmt.execute(query);

			// model.addAttribute("attribute", "redirectWithRedirectPrefix");
			return new ModelAndView("redirect:/api/items/" + itemID, model);
			//return "it works!";

		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		//return query+" does not work";
		return null;
	}
	
	
	@GetMapping(value = "/api/test/items/{itemID}")
	public Item getItemTest(@PathVariable("itemID") int itemID) throws SQLException {

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
				//String imageURL = imageBaseURI + imageID;
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
	
	
	
	
	
}
