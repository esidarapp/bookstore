package com.example.spring.dto.order;

import jakarta.validation.constraints.NotBlank;

public record OrderItemRequestDto(
        @NotBlank
        String shippingAddress
) {
}
