package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.ItemService;

/*
 * @GetMapping is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).

@GetMapping is the newer annotaion. It supports consumes

Consume options are :

consumes = "text/plain"
consumes = {"text/plain", "application/*"}
@RequestMapping is a class level

@GetMapping is a method-level

With sprint Spring 4.3. and up things have changed. 
Now you can use @GetMapping on the method that will handle the http request. 
The class-level @RequestMapping specification is refined with the (method-level)@GetMapping annotation


@RequestMapping("/orders")/* The @Request-Mapping annotation, when applied
                            at the class level, specifies the kind of requests 
                            that this controller handles* 

public class OrderController {

@GetMapping("/current")@GetMapping paired with the classlevel
                        @RequestMapping, specifies that when an 
                        HTTP GET request is received for /order, 
                        orderForm() will be called to handle the request..
                        
public String orderForm(Model model) {

model.addAttribute("order", new Order());

return "orderForm";
}


 */


@RestController
public class MainController {
	

	@GetMapping("/api/home")
	public String loadHome() {
	      return "home";
	   }
	
	//Use this to test the connection
	@RequestMapping("/api/hello")
	public String sayHello() {
		return "Hello do I work??????";
	}

	   


	
	
	
	
	
	
	
	
	
	
}
