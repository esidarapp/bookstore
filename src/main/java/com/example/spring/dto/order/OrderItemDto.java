package com.example.spring.dto.order;

public record OrderItemDto(
        Long id,
        Long bookId,
        int quantity
){
}
