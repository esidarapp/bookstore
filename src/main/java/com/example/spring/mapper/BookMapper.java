package com.example.spring.mapper;

import com.example.spring.config.MapperConfig;
import com.example.spring.dto.BookDto;
import com.example.spring.dto.CreateBookRequestDto;
import com.example.spring.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
