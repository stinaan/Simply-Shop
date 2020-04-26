package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity(name="user")
public class User{
  public User(){}
  public User(String firstName, String lastName, String username, String password) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;

  }
  @Id

  @GeneratedValue(strategy=GenerationType.AUTO)
  private long userID;

  @Column(name = "firstName", nullable = false)
  @NotEmpty(message = "*Please provide your first name")
  private String firstName;

  @Column(name = "lastName", nullable = false)
  @NotEmpty(message = "*Please provide your last name")
  private String lastName;
  
  @Column(name = "username", nullable = false, unique = true)
  @NotEmpty(message = "*Please enter a username")
  private String username;

  @Column(name = "password", nullable = false)
  @Length(min = 3, message = "*Password needs to be atleast a length of 3 characters")
  @NotEmpty(message = "*Please enter a password")
  private String password;
  
  
  //set
  public void setUserName(String userName) {
      this.username = userName;
  }

  public void setFirstName(String first) {
	  this.firstName = first;
  }
  public void setLastName(String last) {
	  this.lastName = last;
  }
    public void setPassword(String aPassword) {
      this.password = aPassword;
  }
    
    //get
  public String getPassword() {
      return password;
  }
  public String getUserName() {
      return username;
  }

  public String getFirstName() {
	  return firstName;
  }
  public String getLastName() {
	  return lastName;
  }
  
  public String getAllInfo() {
      return "[" + this.userID + "," + this.username + "," + this.password + "," + this.firstName + "," + this.lastName + "]";
  }




}