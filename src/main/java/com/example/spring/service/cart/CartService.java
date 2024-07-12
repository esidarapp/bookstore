package com.example.spring.service.cart;

import com.example.spring.dto.cartitem.CartItemDto;
import com.example.spring.dto.cartitem.CartItemRequestDto;
import com.example.spring.dto.cartitem.UpdateCartItemRequestDto;
import com.example.spring.dto.shoppingcart.ShoppingCartDto;
import org.springframework.data.domain.Pageable;

public interface CartService {
    public ShoppingCartDto save(String email, CartItemRequestDto cartItemRequestDto);

    ShoppingCartDto find(String email,Pageable pageable);

    CartItemDto updateById(String email, Long id, UpdateCartItemRequestDto requestDto);

    void deleteById(String email, Long id);
}
