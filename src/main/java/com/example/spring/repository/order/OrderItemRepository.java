package com.example.spring.repository.order;

import com.example.spring.model.OrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = {"book", "order"})
    Optional<OrderItem> findByIdAndOrderIdAndOrder_User_Id(Long itemId, Long orderId,Long userId);
}
