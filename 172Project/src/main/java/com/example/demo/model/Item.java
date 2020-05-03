package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
@Entity(name="item")
public class Item {
	//private String name, category, price, quantity, description, id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Integer itemID;
	
	@Column(name = "name", nullable = false, unique = true)
    @Length(min = 3, message = "*Name must have at least 3 characters")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity", nullable = false)
    @Min(value = 0, message = "*Quantity can not be less than 0")
    private Integer quantity;

    @Column(name = "price", nullable = false)
    @Min(value = 0, message = "*Price can not be less than 0")
    private Double price;
    
    @Column(name = "category", nullable = false)
    private String category;
	
    @OneToOne(cascade = {CascadeType.ALL})
    private ItemImage itemImage;
	

	public Item(String name, String category, Double price, Integer quantity, String description, Integer id, ItemImage image) {
		super();
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.itemID = id;
		this.itemImage = image;
	}
	public Item(String name, String category, Double price, Integer quantity, String description, Integer id) {
		super();
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.itemID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return this.itemID;
	}

	public void setId(Integer id) {
		this.itemID = id;
	}

	public Item() {
		super();
	}
	
	//added this for image for item -Christina
    public ItemImage getImage() {
        return itemImage;
    }
 
    public void setImage(ItemImage image) {
        this.itemImage = image;
    }
	

}
