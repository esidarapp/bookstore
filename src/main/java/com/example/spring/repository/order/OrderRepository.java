package com.example.spring.repository.order;

import com.example.spring.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems"})
    List<Order> findAllByUserId(Long userId);

    @EntityGraph(attributePaths = {"orderItems"})
    Optional<Order> findByIdAndUserId(Long id, Long userId);
}
