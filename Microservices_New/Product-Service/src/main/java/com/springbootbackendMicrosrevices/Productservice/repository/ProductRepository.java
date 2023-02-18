package com.springbootbackendMicrosrevices.Productservice.repository;

import com.springbootbackendMicrosrevices.Productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
