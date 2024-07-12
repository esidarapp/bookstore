package com.example.spring.dto.cartitem;

import jakarta.validation.constraints.NotNull;

public record CartItemRequestDto(
        @NotNull
        Long bookId,
        @NotNull
        int quantity
) {
}
