package com.example.springbootcrud.service;

import com.example.springbootcrud.model.Category;
import com.example.springbootcrud.model.Product;

import java.util.List;

public interface CategoryService {
	List<Object> getAllCategory();
	
	Category add(Category category);
	
	String delete(Long id);
	
	Category update(Category category);

	List<Product> getAllProduct();

}
