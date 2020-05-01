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
import org.springframework.web.multipart.MultipartFile;

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
  //adds user to repo
  @PostMapping(value = "/api/items") 
  public @ResponseBody Item createItem(
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
    
    //finds the user in repo
    @GetMapping(value = "/api/items/{itemID}")
    public Item getItem(@PathVariable("itemID") int itemID) {
      
    	Item item = itemRepo.findById(itemID).get();
      return item;

  }
    
    //finds all items in repo
    @GetMapping(value = "/api/items")
    public List<Item> getItems() {
      
      return (List<Item>) itemRepo.findAll();
    }
    
    
    
    //deletes a user in repo
    @DeleteMapping(value = "/api/items/{itemID}")
    public void removeItem(@PathVariable("itemID") int itemID, HttpServletResponse httpResponse) {
      
      if(itemRepo.existsById(itemID)){
    	  Item item = itemRepo.findById(itemID).get();
        //fileArchiveService.deleteImageFromS3(customer.getCustomerImage());
    	  itemRepo.delete(item); 
    	  imageService.deleteImage(item.getImage());

      }
      httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
