package com.example.demo.model;

import java.util.Date;

import javax.persistence.*;

@Entity(name="order")
public class Order{
  public Order(){}
  public Order(String orderID, Date date, int orderNum, double total, String customerName, String address, String phoneNum) {
    super();
    this.orderID = orderID;
    this.date = date;
    this.orderNum = orderNum;
    this.total = total;
    this.customerName = customerName;
    this.address = address;
    this.phoneNum = phoneNum;

  }
  
  @Id
  @Column(name = "orderID", length = 50)
  private String orderID;

  @Column(name = "date", nullable = false)
  private Date date;

  @Column(name = "orderNum", nullable = false)
  private int orderNum;

  @Column(name = "total", nullable = false)
  private double total;

  @Column(name = "customerName", length = 255, nullable = false)
  private String customerName;

  @Column(name = "address", length = 255, nullable = false)
  private String address;

  @Column(name = "phoneNum", length = 128, nullable = false)
  private String phoneNum;
  
  
//set
  public void setOrderID(String id) {
      this.orderID = id;
  }

  public void setOrderDate(Date orderDate) {
	  this.date = orderDate;
  }
  public void setOrderNum(int num) {
	  this.orderNum = num;
  }
    public void setCost(double cost) {
      this.total = cost;
  }
    public void setCustomerName(String name) {
        this.customerName = name;
    }
    public void setAddress(String addy) {
        this.address = addy;
    }
    public void setPhone(String phone) {
        this.phoneNum = phone;
    }
    
    //get
    public String getOrderID() {
        return this.orderID;
    }

    public Date getOrderDate() {
  	  return this.date;
    }
    public int getOrderNum() {
  	  return this.orderNum;
    }
      public double getCost() {
        return this.total;
    }
      public String getCustomerName() {
          return this.customerName;
      }
      public String getAddress() {
          return this.address;
      }
      public String getPhone() {
          return this.phoneNum;
      }
  
  public String getAllInfo() {
      return "[" + this.orderID + "," + this.date + "," + this.orderNum + "," + this.total + "," + this.customerName +  "," + this.address + "," + this.phoneNum +"]";
  }
}
  
  
