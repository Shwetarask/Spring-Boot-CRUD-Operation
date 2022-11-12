package com.example.springbootcrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootcrud.model.Category;

import com.example.springbootcrud.service.CategoryService;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/ category")
	public List<Object> index() {
		return  categoryService.getAllCategory();
	}
	
	@PostMapping("/ category")
	public Category add(@RequestBody  category newCategory) {
		
		return  categoryService.add(newCategory);
	}
	
	@DeleteMapping("/ category/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        return  categoryService.delete(id);
    }
	
	@PutMapping("/ category")
	public Category update(@RequestBody Category  category) {
		return  categoryService.update( category);
	}
}

