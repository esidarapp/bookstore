package com.example.spring.dto.shoppingcart;

import com.example.spring.dto.cartitem.CartItemDto;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record ShoppingCartDto(
        @NotNull
        Long id,
        @NotNull
        Long userId,
        @NotNull
        Set<CartItemDto> cartItems
){
}
