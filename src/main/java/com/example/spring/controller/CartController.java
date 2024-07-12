package com.example.spring.controller;

import com.example.spring.dto.cartitem.CartItemDto;
import com.example.spring.dto.cartitem.CartItemRequestDto;
import com.example.spring.dto.cartitem.UpdateCartItemRequestDto;
import com.example.spring.dto.shoppingcart.ShoppingCartDto;
import com.example.spring.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping Cart management", description = "Endpoints for managing shopping carts")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Add item to shopping cart",
            description = "Add a new item to the user's shopping cart")
    public void save(@RequestBody @Valid CartItemRequestDto cartItemRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        cartService.save(email, cartItemRequestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get user's shopping cart",
            description = "Retrieve the user's shopping cart")
    public ShoppingCartDto find(@ParameterObject @PageableDefault Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return cartService.find(email,pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/items/{id}")
    @Operation(summary = "Update item in shopping cart",
            description = "Update the quantity or other properties of an item in the shopping cart")
    public CartItemDto updateById(@PathVariable Long id,
                                  @RequestBody @Valid UpdateCartItemRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return cartService.updateById(email, id, requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/items/{id}")
    @Operation(summary = "Remove item from shopping cart",
            description = "Delete an item from the user's shopping cart")
    public void deleteById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        cartService.deleteById(email, id);
    }
}
