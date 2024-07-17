package com.example.spring.dto.order;

import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDto(
        @NotNull
        String shippingAddress
) {
}
