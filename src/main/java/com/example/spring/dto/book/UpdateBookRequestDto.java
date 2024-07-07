package com.example.spring.dto.book;

import com.example.spring.validate.annotations.CoverImage;
import com.example.spring.validate.annotations.Isbn;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class UpdateBookRequestDto {
    private String title;
    private String author;
    @Isbn
    private String isbn;
    @Min(0)
    private BigDecimal price;
    private String description;
    @CoverImage
    private String coverImage;
    private List<Long> categoryIds;
}
