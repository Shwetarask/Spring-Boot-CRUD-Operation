package com.example.springbootcrud.service;

import com.example.springbootcrud.clients.Redis;
import com.example.springbootcrud.model.Category;
import com.example.springbootcrud.model.Product;
import com.example.springbootcrud.repository.CategoryRepository;
import com.example.springbootcrud.repository.ProductRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public List<Object> getAllCategory() {
		JedisPool jedis = Redis.getPoolInstance();
		Set<String> keys = jedis.getResource().keys("course-*");

		List<String> values = Collections.emptyList();
		if( !keys.isEmpty() ) {
			values = jedis.getResource().mget(keys.toArray(new String[keys.size()]));
		}

		if( CollectionUtils.isEmpty(values) ) {
			//retrieve from database, add into cache and then return the value
			List<Object> category = (List) categoryRepository.findAll();

			//edge case when there are no values in cache but exists in db

			return category;
		}

		Gson gson = new Gson();
		return  values.stream()
				.map(category -> gson.fromJson(category.toString(), (Type) Category.class)
				).collect(Collectors.toList());
	}
	
	@Override
	public Category add(Category newCategory) {
		Category dbResponse =categoryRepository.save(newCategory);

		String productkey = "category-" + dbResponse.getId();
		this.updateProductFromRedis(productkey, newCategory);

		

		return dbResponse;
	}
	
	@Override
	public String delete(Long id) {
		try {
			categoryRepository.deleteById(id);

			String categoryKey = "category-" + id;
			this.deleteProductFromRedis(categoryKey);

		}catch(Exception err) {
			System.out.print(err.toString());
			return "Failed to delete Category with id " + id;
		}
	
		return "Deleted Category with id " + id;
	}
	
	@Override
	public Category update(Category category) {
		if(!categoryRepository.existsById(category.getId())) {
			return null;
		}

		Category dbResponse = categoryRepository.save(category);

		String categoryKey = "category-" + dbResponse.getId();
		this.updateStudentFromRedis(categoryKey, category);

		return category;
	}

	private void updateProductFromRedis(String key, Category obj) {

		JedisPool jedis = Redis.getPoolInstance();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		jedis.getResource().set(key, gson.toJson(obj));
	}

	private void deleteProductFromRedis(String key) {

		JedisPool jedis = Redis.getPoolInstance();
		jedis.getResource().del(key);

	}
}
