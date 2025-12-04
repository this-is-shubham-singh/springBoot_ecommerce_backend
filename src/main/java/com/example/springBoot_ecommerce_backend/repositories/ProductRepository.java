package com.example.springBoot_ecommerce_backend.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.springBoot_ecommerce_backend.domains.Category;
import com.example.springBoot_ecommerce_backend.domains.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findByBrandAndName(String brandName, String name);
    List<Product> findByCategory(Category category);
    List<Product> findByBrand(String brandName);
    Optional<Product> findByName(String name); 
    List<Product> findByCategoryAndBrand(Category category, String brand);
    Optional<Product> findByNameAndBrand(String name, String brand);
    int countByBrandAndName(String brand, String name);
}

