package com.example.spring.mapper;

import com.example.spring.config.MapperConfig;
import com.example.spring.dto.BookDto;
import com.example.spring.dto.CreateBookRequestDto;
import com.example.spring.model.Book;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateBookFromDto(CreateBookRequestDto requestDto, @MappingTarget Book book);

    List<BookDto> toDtoList(List<Book> books);
}
