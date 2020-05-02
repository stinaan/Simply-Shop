package com.example.model;

import javax.persistence.*;

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

  @Column(nullable = false, length = 30)
  private String firstName;

  @Column(nullable = false, length = 30)
  private String lastName;
  
  @Column(nullable = false, length = 30)
  private String username;

  @Column(nullable = false, length = 30)
  private String password;
}