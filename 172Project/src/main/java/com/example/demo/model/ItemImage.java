package com.example.demo.model;

import javax.persistence.*;

@Entity(name="itemImage")
public class ItemImage {
	
	 public ItemImage(String imageKey, String imageURL) {
		    this.imageKey = imageKey;
		    this.imageURL =imageURL; 
		  }
	 
	 public ItemImage() {}
	 
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		  private Integer itemImageID;
		  
		  @Column(name = "imageKey", nullable = false)
		  private String imageKey;
		  
		  
		  @Column(name = "imageURL", nullable = false)
		  private String imageURL;
		  
		  
		  //set
		  public void setImageKey(String imageKey) {
		      this.imageKey = imageKey;
		  }

		  public void setImageURL(String imageURL) {
			  this.imageURL = imageURL;
		  }
		    
		    //get
		  public String getImageKey() {
		      return imageKey;
		  }
		  public String getImageURL() {
		      return imageURL;
		  }
		  
		  
		  
		  

}
