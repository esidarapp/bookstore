package com.example.spring.mapper;

import com.example.spring.config.MapperConfig;
import com.example.spring.dto.shoppingcart.CartItemDto;
import com.example.spring.dto.shoppingcart.CartItemRequestDto;
import com.example.spring.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public interface CartItemMapper {
    CartItem toModel(CartItemRequestDto requestDto);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);
}

