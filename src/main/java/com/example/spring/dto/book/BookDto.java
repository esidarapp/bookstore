package com.example.spring.dto.book;

import java.math.BigDecimal;
import java.util.List;
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
}
