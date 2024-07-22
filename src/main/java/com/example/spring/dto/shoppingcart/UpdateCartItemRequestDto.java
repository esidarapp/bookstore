package com.example.spring.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateCartItemRequestDto(
        @NotNull
        @Positive
        int quantity
){
}
