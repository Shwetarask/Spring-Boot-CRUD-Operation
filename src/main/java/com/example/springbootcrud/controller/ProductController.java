package com.example.springbootcrud.controller;

import com.example.springbootcrud.model.Product;
import com.example.springbootcrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
	//Display list of product
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/product")
	public List<Object> viewHomePage() {

		List<Object> product = productService.getAllProduct();

		return product;
	}
	
	@PostMapping("/product")
	public Product add(@RequestBody Product newProduct {
		return productService.add(newProduct);
	}
	
	@PutMapping("/product")
	public Product update(@RequestBody Product updateProduct) {
		return productService.update(updateStudent);
	}
	
	@DeleteMapping("/product/{id}")
	public String delete(@PathVariable Long id) {
		return productService.delete(id);
	}
	
}

