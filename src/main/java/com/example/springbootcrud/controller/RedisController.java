package com.example.springbootcrud.controller;

import com.example.springbootcrud.clients.Redis;
import com.example.springbootcrud.model.Product;
import com.example.springbootcrud.service.ProductService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RedisController {

    @Autowired
    private ProductService productService;

    @GetMapping("/redis-product")
    public List<Object> viewRedisStudents() {

        JedisPool jedis = Redis.getPoolInstance();

        Set<String> keys = jedis.getResource().keys("product-*");
        List<String> values = jedis.getResource().mget(keys.toArray(new String[keys.size()]));

        if( CollectionUtils.isEmpty(values) ) {

            //retrieve from database, add into cache and then return the value
            List<Object> product

            //add into cache and return

            return product;
        }

        Gson gson = new Gson();
        return  values.stream()
                .map(student -> gson.fromJson(student.toString(), (Type) Student.class)
                ).collect(Collectors.toList());

    }
}
