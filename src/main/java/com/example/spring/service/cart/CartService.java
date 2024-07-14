package com.example.spring.service.cart;

import com.example.spring.dto.cartitem.CartItemRequestDto;
import com.example.spring.dto.cartitem.UpdateCartItemRequestDto;
import com.example.spring.dto.shoppingcart.ShoppingCartDto;
import com.example.spring.model.User;

public interface CartService {
    ShoppingCartDto addBookToCart(Long userId, CartItemRequestDto cartItemRequestDto);

    ShoppingCartDto getUsersCart(Long userId);

    ShoppingCartDto updateById(Long userId, Long itemId, UpdateCartItemRequestDto requestDto);

    void deleteById(Long userId, Long itemId);

    void createShoppingCart(User user);
}
