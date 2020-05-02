package com.example.session;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.model.Cart;

public class CurrentCart {
	 
	   // Products in the cart, stored in Session.
	   public static Cart getCurrentCart(HttpServletRequest request) {
	 
	      Cart cart = (Cart) request.getSession().getAttribute("currentCart");
	 
	    
	      if (cart == null) { //if session has no cart var set, create one
	         cart = new Cart();
	          
	         request.getSession().setAttribute("currentCart", cart);
	      }
	      return cart;
	   }
	   
	 
	   public static void deleteCart(HttpServletRequest request) {
	      request.getSession().removeAttribute("currentCart");
	   }
	 
}

