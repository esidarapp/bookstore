package com.example.spring.service.cart;

import com.example.spring.dto.shoppingcart.CartItemRequestDto;
import com.example.spring.dto.shoppingcart.ShoppingCartDto;
import com.example.spring.dto.shoppingcart.UpdateCartItemRequestDto;
import com.example.spring.exception.EntityNotFoundException;
import com.example.spring.mapper.CartItemMapper;
import com.example.spring.mapper.ShoppingCartMapper;
import com.example.spring.model.Book;
import com.example.spring.model.CartItem;
import com.example.spring.model.ShoppingCart;
import com.example.spring.model.User;
import com.example.spring.repository.book.BookRepository;
import com.example.spring.repository.cart.CartItemRepository;
import com.example.spring.repository.cart.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public ShoppingCartDto addBookToCart(Long userId, CartItemRequestDto cartItemRequestDto) {
        Book book = bookRepository.findById(cartItemRequestDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book not found with id " + cartItemRequestDto.bookId()));
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        cartItem.setBook(book);
        ShoppingCart shoppingCart = findByUserId(userId);
        boolean itemExists = shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(cartItemRequestDto.bookId()))
                .findFirst()
                .map(existingItem -> {
                    existingItem.setQuantity(
                            existingItem.getQuantity() + cartItemRequestDto.quantity());
                    cartItemRepository.save(existingItem);
                    return true;
                })
                .orElse(false);
        if (!itemExists) {
            cartItem.setShoppingCart(shoppingCart);
            shoppingCart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto getUsersCart(Long userId) {
        ShoppingCart shoppingCart = findByUserId(userId);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateById(Long userId,
                                      Long itemId,
                                      UpdateCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = findByUserId(userId);
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(itemId,
                        shoppingCart.getId())
                .map(item -> {
                    item.setQuantity(requestDto.quantity());
                    return item;
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart item with id " + itemId + " not found in shopping cart"));
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteById(Long userId, Long id) {
        ShoppingCart shoppingCart = findByUserId(userId);
        CartItem cartItemToDelete = cartItemRepository
                .findByIdAndShoppingCartId(id, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart item with id " + id + " not found in shopping cart"));
        shoppingCart.getCartItems().remove(cartItemToDelete);
        cartItemRepository.delete(cartItemToDelete);
        shoppingCartRepository.save(shoppingCart);
    }

    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart findByUserId(Long id) {
        return shoppingCartRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException(
                                "Can't find shopping cart for user by id " + id));
    }
}
