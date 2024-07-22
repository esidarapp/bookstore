package com.example.spring.dto.order;

import com.example.spring.model.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDto(
        Long id,
        Long userId,
        Set<OrderItemDto> orderItems,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime orderDate,
        BigDecimal total,
        Order.Status status
) {
}
