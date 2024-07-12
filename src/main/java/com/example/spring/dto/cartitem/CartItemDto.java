package com.example.spring.dto.cartitem;

import jakarta.validation.constraints.NotNull;

public record CartItemDto(
        Long id,
        Long bookId,
        String bookTitle,
        @NotNull
        int quantity
) {}
