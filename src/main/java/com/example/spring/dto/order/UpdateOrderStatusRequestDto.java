package com.example.spring.dto.order;

import com.example.spring.model.Order;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequestDto(
        @NotNull
        Order.Status status
) {
}
