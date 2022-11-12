package com.example.springbootcrud.service;

import com.example.springbootcrud.model.Category;
import com.example.springbootcrud.model.Product;

import java.util.List;

public interface ProductService {
	List<Object> getAllProduct();
	
	Product add(Product product);
	
	Product update(Product product);
	
	String delete(Long id);
	
	List<Category> getAllCategory();
}
