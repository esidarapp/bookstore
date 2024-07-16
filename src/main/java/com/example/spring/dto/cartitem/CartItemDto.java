package com.example.spring.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CartItemDto(
        @NotNull
        Long id,
        @NotNull
        Long bookId,
        @NotBlank
        String bookTitle,
        @NotNull
        @Min(1)
        int quantity
) {}
