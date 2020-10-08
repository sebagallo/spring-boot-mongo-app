package com.sebag.productapp.repositories;

import com.sebag.productapp.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{'deleted': false}")
    List<Product> getAllProducts();
}
