package com.example.spring.dto.book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;
    private List<Long> categoryIds;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id)
                && Objects.equals(title, bookDto.title)
                && Objects.equals(author, bookDto.author)
                && Objects.equals(isbn, bookDto.isbn)
                && Objects.equals(price, bookDto.price)
                && Objects.equals(description, bookDto.description)
                && Objects.equals(coverImage, bookDto.coverImage)
                && Objects.equals(
                        categoryIds.stream().sorted().collect(Collectors.toList()),
                        bookDto.categoryIds.stream().sorted().collect(Collectors.toList())
                );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, isbn, price, description, coverImage,
                categoryIds.stream().sorted().collect(Collectors.toList()));
    }
}

