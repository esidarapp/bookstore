package com.example.spring.dto;

import com.example.spring.validate.annotations.CoverImage;
import com.example.spring.validate.annotations.Isbn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @Isbn
    private String isbn;
    @Min(0)
    private BigDecimal price;
    @NotNull
    private String description;
    @CoverImage
    private String coverImage;
}
