package com.example.spring.repository.cartitem;

import com.example.spring.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @EntityGraph(attributePaths = {"book", "shoppingCart"})
    Optional<CartItem> findByIdAndShoppingCartId(Long id, Long shoppingCartId);
}
