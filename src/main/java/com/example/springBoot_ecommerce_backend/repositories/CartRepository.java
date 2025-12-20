package com.example.springBoot_ecommerce_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springBoot_ecommerce_backend.domains.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
