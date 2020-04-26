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

import com.example.demo.model.User;
import com.example.repository.UserRepository;




@RestController
public class UserController {
  
  @Autowired
  private UserRepository userRepo;
 // @Autowired
  //private FileArchiveService fileArchiveService;
  //adds user to repo
  @PostMapping(value = "/users")
  public @ResponseBody User createUser(@RequestParam(value="firstName", required=true) String firstName,
                         @RequestParam(value="lastName", required=true) String lastName,
                         @RequestParam(value="username", required=true) String username,
                         @RequestParam(value="password", required=true) String password) throws Exception {
   // CustomerImage customerImage = fileArchiveService.saveFileToS3(image); 
    User user = new User(firstName, lastName, username, password);
    userRepo.save(user);
    return user; 
  }
    
    //finds the user in repo
    @GetMapping(value = "/users/{userID}")
    public User getCustomer(@PathVariable("userID") int userID) {
      

      /* validate customer Id parameter */
      User customer = userRepo.findById(userID).get();
      return customer;

  }
    
    //finds all users in repo
    @GetMapping(value = "/users")
    public List<User> getUsers() {
     
      return (List<User>) userRepo.findAll();
    }
    
    //deletes a user in repo
    @DeleteMapping(value = "/users/{userID}")
    public void removeCustomer(@PathVariable("userID") int userID, HttpServletResponse httpResponse) {
      
      if(userRepo.existsById(userID)){
    	  User user = userRepo.findById(userID).get();
        //fileArchiveService.deleteImageFromS3(customer.getCustomerImage());
    	  userRepo.delete(user); 
      }
      httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
