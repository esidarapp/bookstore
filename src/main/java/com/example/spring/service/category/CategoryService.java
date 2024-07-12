package com.example.spring.service.category;

import com.example.spring.dto.book.CreateBookRequestDto;
import com.example.spring.dto.book.UpdateBookRequestDto;
import com.example.spring.dto.category.CategoryDto;
import com.example.spring.dto.category.CategoryRequestDto;
import com.example.spring.model.Category;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<CategoryDto> findAll();

    CategoryDto getById(Long id);

    CategoryDto save(CategoryRequestDto requestDto);

    CategoryDto update(Long id, CategoryRequestDto requestDto);

    void deleteById(Long id);

    Set<Category> mapCategoryIdsToCategories(CreateBookRequestDto requestDto);

    Set<Category> mapCategoryIdsToCategories(UpdateBookRequestDto requestDto);
}
