package com.example.spring.mapper;

import com.example.spring.config.MapperConfig;
import com.example.spring.dto.cartitem.CartItemDto;
import com.example.spring.dto.cartitem.CartItemRequestDto;
import com.example.spring.exception.EntityNotFoundException;
import com.example.spring.model.Book;
import com.example.spring.model.CartItem;
import com.example.spring.repository.book.BookRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class CartItemMapper {
    @Autowired
    protected BookRepository bookRepository;

    @Mapping(source = "bookId", target = "book", qualifiedByName = "bookFromId")
    public abstract CartItem toModel(CartItemRequestDto requestDto);

    @Mapping(source = "book", target = "bookId", qualifiedByName = "idFromBook")
    @Mapping(source = "book.title", target = "bookTitle")
    public abstract CartItemDto toDto(CartItem cartItem);

    @Named("bookFromId")
    public Book bookFromId(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find book by id " + id));
    }

    @Named("idFromBook")
    public Long idFromBook(Book book) {
        return book != null ? book.getId() : null;
    }
}

