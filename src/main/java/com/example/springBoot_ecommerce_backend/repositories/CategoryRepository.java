package com.example.springBoot_ecommerce_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springBoot_ecommerce_backend.domains.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>  {
    Optional<Category> findByName(String name);
}
