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
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}
	
	@Override
	public List<Object> getAllProduct() {

		JedisPool jedis = Redis.getPoolInstance();
		Set<String> keys = jedis.getResource().keys("product-*");

		List<String> values = Collections.emptyList();
		if( !keys.isEmpty() ) {
			values = jedis.getResource().mget(keys.toArray(new String[keys.size()]));
		}

		if( CollectionUtils.isEmpty(values) ) {
			//retrieve from database, add into cache and then return the value
			List<Object> product = (List) productRepository.findAll();

			//edge case when there are no values in cache but exists in db

			return product;
		}

		Gson gson = new Gson();
		return  values.stream()
				.map(product -> gson.fromJson(product.toString(), (Type) product.class)
				).collect(Collectors.toList());
	}
	
	@Override
	public Product add(Product newProduct) {

		Product dbResponse = productRepository.save(newProduct);

		String productkey = "product-" + dbResponse.getId();
		this.updateProductFromRedis(productkey, newProduct);

		//fetch all courses and add them in cache

		return dbResponse;
	}
	
	@Override
	public String delete(Long id) {
		try {
			productRepository.deleteById(id);

			String productkey = "product-" + id;
			this.deleteProductFromRedis(productkey);

		}catch(Exception err) {
			return "Failed to delete Product with id" + id;
		}
		return "Deleted Product with id " + id;
	}
	
	@Override
	public Product update(Product product) {

		if(!productRepository.existsById(product.getId())) {
			return null;
		}
		
		Product dbResponse = productRepository.save(product);

		String productkey = "product-" + dbResponse.getId();
		this.updateProductFromRedis(productkey, product);

		return dbResponse;
	}

	private void updateProductFromRedis(String key, Productobj) {

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
