package com.example.controllers;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Item;
import com.example.repository.ItemRepository;




@RestController
public class ItemController {
  
  @Autowired
  private ItemRepository itemRepo;
 // @Autowired
  //private FileArchiveService fileArchiveService;
  //adds user to repo
  @PostMapping(value = "/items") 
  public @ResponseBody Item createItem(
		  				 @RequestParam(value="name", required=true) String name,
                         @RequestParam(value="category", required=true) String category,
                         @RequestParam(value="price", required=true) double price,
                         @RequestParam(value="quantity", required=true) int quantity,
                         @RequestParam(value="description", required=true) String description,
                         @RequestParam(value="id", required=true) Integer itemID) throws Exception {
   // ItemImage itemImage = fileArchiveService.saveFileToS3(image); 
	Item item = new Item(name,  category,  price,  quantity,  description,  itemID);
    itemRepo.save(item);
    return item; 
  }
    
    //finds the user in repo
    @GetMapping(value = "/items/{itemID}")
    public Item getItem(@PathVariable("itemID") int itemID) {
      
      /* validate customer Id parameter */
    	Item item = itemRepo.findById(itemID).get();
      return item;

  }
    
    //finds all items in repo
    @GetMapping(value = "/items")
    public List<Item> getItems() {
      
      return (List<Item>) itemRepo.findAll();
    }
    
    //deletes a user in repo
    @DeleteMapping(value = "/items/{itemID}")
    public void removeItem(@PathVariable("itemID") int itemID, HttpServletResponse httpResponse) {
      
      if(itemRepo.existsById(itemID)){
    	  Item item = itemRepo.findById(itemID).get();
        //fileArchiveService.deleteImageFromS3(customer.getCustomerImage());
    	  itemRepo.delete(item); 
      }
      httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
