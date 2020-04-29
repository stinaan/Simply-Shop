package com.example.demo.model;

public class Cart {
	  private int orderNum;
	  //TO-DO: still need to finish this
	  //To-Do: 
	    private User user;
	 
	    //private final List<CartInfo> cartLines = new ArrayList<CartInfo>();
	    //array of details in cart
	 
	    public Cart() {
	 
	    }
	 
	    //get
	    public int getOrderNum() {
	        return orderNum;
	    }
	 
	    public User getUser() {
	        return user;
	    }
	 
	    //set
	    public void setCustomerInfo(User user) {
	        this.user = user;
	    }

	    public void setOrderNum(int num) {
	        this.orderNum = num;
	    }
	    


}
