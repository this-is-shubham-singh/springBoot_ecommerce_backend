import java.math.BigDecimal;

import com.example.springBoot_ecommerce_backend.domains.Cart;

public interface CartInterface {

    public Cart getCart(Long id);

    public String clearCart(Long id);

    public BigDecimal getTotalPrice(Long id);
}