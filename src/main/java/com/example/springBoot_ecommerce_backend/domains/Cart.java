package com.example.springBoot_ecommerce_backend.domains;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    public void addCartItem(CartItem cartItem) {
        cartItem.setCart(this);
        this.getCartItems().add(cartItem);
        updateTotalPrice();
    }

    public void removeCartItem(CartItem cartItem) {
        cartItem.setCart(null);
        this.getCartItems().remove(cartItem);
        updateTotalPrice();
    }

    public void updateTotalPrice() {
        BigDecimal total = this.getCartItems().stream()
                .map(cartItem -> BigDecimal.valueOf(cartItem.getQuantity()).multiply(cartItem.getUnitPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.setTotalPrice(total);
    }

}
