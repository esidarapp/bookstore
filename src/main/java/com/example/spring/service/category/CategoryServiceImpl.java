package com.example.spring.service.category;

import com.example.spring.dto.book.CreateBookRequestDto;
import com.example.spring.dto.book.UpdateBookRequestDto;
import com.example.spring.dto.category.CategoryDto;
import com.example.spring.dto.category.CategoryRequestDto;
import com.example.spring.exception.EntityNotFoundException;
import com.example.spring.mapper.CategoryMapper;
import com.example.spring.model.Category;
import com.example.spring.repository.category.CategoryRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id " + id)
        );
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto save(CategoryRequestDto categoryDto) {
        Category newCategory = categoryMapper.toModel(categoryDto);
        categoryRepository.save(newCategory);
        return categoryMapper.toDto(newCategory);
    }

    @Override
    public CategoryDto update(Long id, CategoryRequestDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id " + id)
        );
        categoryMapper.updateCategoryFromDto(categoryDto, category);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Set<Category> mapCategoryIdsToCategories(CreateBookRequestDto requestDto) {
        List<Long> categoryIds = requestDto.getCategoryIds();
        List<Category> categoriesList = categoryRepository.findAllById(categoryIds);
        if (categoriesList.size() != categoryIds.size()) {
            throw new EntityNotFoundException("One or more categories not found");
        }
        return new HashSet<>(categoriesList);
    }

    @Override
    public Set<Category> mapCategoryIdsToCategories(UpdateBookRequestDto requestDto) {
        List<Long> categoryIds = requestDto.getCategoryIds();
        List<Category> categoriesList = categoryRepository.findAllById(categoryIds);
        if (categoriesList.size() != categoryIds.size()) {
            throw new EntityNotFoundException("One or more categories not found");
        }
        return new HashSet<>(categoriesList);
    }
}
