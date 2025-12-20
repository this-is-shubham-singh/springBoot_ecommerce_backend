package com.example.springBoot_ecommerce_backend.services.cartItem;

public interface CartItemInterface {
    public String addItemToCart(Long cartId, int quantity, Long productId);

    public String removeItemFromCart(Long cartId, Long productId);

    public String updateCartItemQuantity(Long cartId, Long productId, int quantity);
}
