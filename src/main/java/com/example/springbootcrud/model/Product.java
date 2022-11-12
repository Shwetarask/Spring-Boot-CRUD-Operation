package com.example.springbootcrud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "created_at")
    private Date createdAt = new Date();
	
	@Column(name = "last_updated_at")
    private Date lastUpdatedAt = new Date();
	
	@ManyToMany(cascade = {CascadeType.REFRESH}, targetEntity = Category.class)
	@JoinTable(name = "product_category", 
	joinColumns = { 
			@JoinColumn(name = "product_id")
			}, 
    inverseJoinColumns = { 
    		@JoinColumn(name = "category_id") 
    		})
	@JsonIgnoreProperties("product")
	private List<Category> category;
    
    public Product() {
    	
    }
    
	public Product(String title) {
		super();
		this.name = title;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}
	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
	public List<Category> getCategory() {
		return category;
	}
	public void setCategory(List<Category> category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", name='" + name + '\'' +
				", createdAt=" + createdAt +
				", lastUpdatedAt=" + lastUpdatedAt +
				", category=" + category +
				'}';
	}

	public String toJsonString() {
		return "{" +
				"id=" + id +
				", name='" + name + '\'' +
				", createdAt=" + createdAt +
				", lastUpdatedAt=" + lastUpdatedAt +
				", category=" + category +
				'}';
	}
}