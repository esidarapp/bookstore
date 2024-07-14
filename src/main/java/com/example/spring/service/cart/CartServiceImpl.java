package com.example.spring.service.cart;

import com.example.spring.dto.cartitem.CartItemDto;
import com.example.spring.dto.cartitem.CartItemRequestDto;
import com.example.spring.dto.cartitem.UpdateCartItemRequestDto;
import com.example.spring.dto.shoppingcart.ShoppingCartDto;
import com.example.spring.exception.EntityNotFoundException;
import com.example.spring.mapper.CartItemMapper;
import com.example.spring.mapper.ShoppingCartMapper;
import com.example.spring.model.CartItem;
import com.example.spring.model.ShoppingCart;
import com.example.spring.repository.cartitem.CartItemRepository;
import com.example.spring.repository.shoppingcart.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public ShoppingCartDto save(String email, CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        ShoppingCart shoppingCart = findByUser(email);
        shoppingCart.getCartItems().add(cartItem);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto find(String email, Pageable pageable) {
        ShoppingCart shoppingCart = findByUser(email);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public CartItemDto updateById(String email, Long id, UpdateCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = findByUser(email);
        CartItem cartItemToUpdate = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart item with id " + id + " not found in shopping cart"));
        cartItemToUpdate.setQuantity(requestDto.quantity());
        cartItemRepository.save(cartItemToUpdate);
        return cartItemMapper.toDto(cartItemToUpdate);
    }

    @Override
    @Transactional
    public void deleteById(String email, Long id) {
        ShoppingCart shoppingCart = findByUser(email);
        CartItem cartItemToDelete = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart item with id " + id + " not found in shopping cart"));
        shoppingCart.getCartItems().remove(cartItemToDelete);
        cartItemRepository.deleteById(id);
        shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCart findByUser(String email) {
        return shoppingCartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                                "Can't find shopping cart for user by email " + email));
    }
}
