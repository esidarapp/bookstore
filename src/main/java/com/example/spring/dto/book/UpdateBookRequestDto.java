package com.example.spring.dto.book;

import com.example.spring.validate.annotations.CoverImage;
import com.example.spring.validate.annotations.Isbn;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateBookRequestDto {
    @Nullable
    private String title;
    @Nullable
    private String author;
    @Nullable
    @Isbn
    private String isbn;
    @Nullable
    @Min(0)
    private BigDecimal price;
    @Nullable
    private String description;
    @Nullable
    @CoverImage
    private String coverImage;
}
