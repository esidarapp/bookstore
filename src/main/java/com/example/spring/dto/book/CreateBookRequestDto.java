package com.example.spring.dto.book;

import com.example.spring.validate.annotations.CoverImage;
import com.example.spring.validate.annotations.Isbn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    @Isbn
    private String isbn;
    @Min(0)
    private BigDecimal price;
    @NotBlank
    private String description;
    @NotBlank
    @CoverImage
    private String coverImage;
}
