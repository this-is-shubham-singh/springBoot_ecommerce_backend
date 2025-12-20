package com.example.springBoot_ecommerce_backend.services.cartItem;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.springBoot_ecommerce_backend.Exception.ResourceNotFoundException;
import com.example.springBoot_ecommerce_backend.domains.Cart;
import com.example.springBoot_ecommerce_backend.domains.CartItem;
import com.example.springBoot_ecommerce_backend.repositories.CartRepository;
import com.example.springBoot_ecommerce_backend.repositories.ProductRepository;
import com.example.springBoot_ecommerce_backend.domains.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements CartItemInterface {

    private CartRepository cartRepository;
    private ProductRepository productRepository;

    @Override
    public String addItemToCart(Long cartId, int quantity, Long productId) {

        // getting cart by id
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart not found"));

        // searching the cartitem using product id
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        // if cartitem is not present, create it
        if (cartItem == null) {

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("product not found"));

            CartItem item = new CartItem();
            item.setQuantity(quantity);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());

            item.updateTotalPrice();

            cart.addCartItem(item);

            cartRepository.save(cart);
            return "cart item added";
        }

        // if cart item is present, just update quantity
        cartItem.setQuantity(quantity);

        // update total price of both cartItem and cart
        cartItem.updateTotalPrice();
        cart.updateTotalPrice();

        cartRepository.save(cart);
        return "cart item updated";
    }

    @Override
    public String removeItemFromCart(Long cartId, Long productId) {

        // get cart by id
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart not found"));

        // get the cartitem from the cart using product id
        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("cart item not found"));

        // remove cartitem from cart
        cart.removeCartItem(cartItem);

        return "item removed from cart";
    }

    @Override
    public String updateCartItemQuantity(Long cartId, Long productId, int quantity) {

        // get cart by id
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart not found"));

        // get cartitem from cart using product id
        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("cartItem not found"));

        // update quantity 
        cartItem.setQuantity(quantity);

        // recalculate total price 
        cartItem.updateTotalPrice();
        cart.updateTotalPrice();
        
        cartRepository.save(cart);

        return "quantity updated";
    }
}
