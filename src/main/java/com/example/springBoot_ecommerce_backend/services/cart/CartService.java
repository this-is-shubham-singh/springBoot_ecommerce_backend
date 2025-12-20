package com.example.springBoot_ecommerce_backend.services.cart;

import java.math.BigDecimal;

import com.example.springBoot_ecommerce_backend.Exception.ResourceNotFoundException;
import com.example.springBoot_ecommerce_backend.domains.Cart;
import com.example.springBoot_ecommerce_backend.repositories.CartRepository;

public class CartService implements CartInterface {

    public CartRepository cartRepository;

    @Override
    public Cart getCart(Long id) {

        return cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("cart not found"));
    }

    @Override
    public String clearCart(Long id) {

        // get cart by id
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("cart not found"));

        // clear cartItem array, it automatically deletes child 
        cart.getCartItems().clear();

        // update total price
        cart.updateTotalPrice();

        cartRepository.save(cart);

        return "cart cleared";
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        

        // get class by id 
    }

}
