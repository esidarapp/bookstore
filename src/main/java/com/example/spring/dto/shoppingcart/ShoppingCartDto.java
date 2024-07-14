package com.example.spring.dto.shoppingcart;

import com.example.spring.dto.cartitem.CartItemDto;
import java.util.Set;

public record ShoppingCartDto(
        Long id,
        Long userId,
        Set<CartItemDto> cartItems
){
}
