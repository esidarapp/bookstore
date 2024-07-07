package com.example.spring.mapper;

import com.example.spring.config.MapperConfig;
import com.example.spring.dto.category.CategoryDto;
import com.example.spring.dto.category.CategoryRequestDto;
import com.example.spring.model.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CategoryRequestDto categoryDto);

    List<CategoryDto> toDtoList(List<Category> categories);

    void updateCategoryFromDto(CategoryRequestDto requestDto, @MappingTarget Category category);
}
