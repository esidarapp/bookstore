package com.example.spring.mapper;

import com.example.spring.config.MapperConfig;
import com.example.spring.dto.book.BookDto;
import com.example.spring.dto.book.BookDtoWithoutCategoryIds;
import com.example.spring.dto.book.CreateBookRequestDto;
import com.example.spring.dto.book.UpdateBookRequestDto;
import com.example.spring.model.Book;
import com.example.spring.model.Category;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    List<BookDto> toDtoList(List<Book> books);

    void updateBookFromDto(UpdateBookRequestDto requestDto, @MappingTarget Book book);

    @AfterMapping
    default void setCategories(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            Set<Category> categories = book.getCategories();
            bookDto.setCategoryIds(
                    categories.stream()
                            .map(Category::getId)
                            .collect(Collectors.toList()));
        }
    }

}
