package com.example.springbootcrud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;


@Entity
@Table( name = "category")
public class Category {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
		
	@ManyToMany(mappedBy = "category")
	@JsonIgnoreProperties("category")
	private List<Product> product;
	
	public Category() {
		
	}
	
	public Category(String name) {
		super();
		this.name = name;
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
	public List<Product> getProduct() {
	   return product;
	}
	  
	public void setProduct(List<Product> product) {
	   this.product = product;
	}

	@Override
	public String toString() {
		return "Course{" +
				"id=" + id +
				", name='" + name + '\'' +
				", product=" + product +
				'}';
	}

	public String toJsonString() {
		return "{" +
				"id=" + id +
				", name='" + name + '\'' +
				", product=" + product +
				'}';
	}
}
