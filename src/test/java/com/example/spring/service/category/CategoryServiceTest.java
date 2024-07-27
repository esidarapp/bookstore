package com.example.spring.service.category;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.spring.dto.book.CreateBookRequestDto;
import com.example.spring.dto.book.UpdateBookRequestDto;
import com.example.spring.dto.category.CategoryDto;
import com.example.spring.dto.category.CategoryRequestDto;
import com.example.spring.exception.EntityNotFoundException;
import com.example.spring.mapper.CategoryMapper;
import com.example.spring.model.Category;
import com.example.spring.repository.category.CategoryRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnListOfCategoryDto() {
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto(1L,"1","1");
        List<Category> categories = List.of(category);
        List<CategoryDto> categoryDtos = List.of(categoryDto);

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toDtoList(categories)).thenReturn(categoryDtos);

        List<CategoryDto> result = categoryMapper.toDtoList(categoryRepository.findAll());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(categoryDto, result.get(0));
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getById_ShouldReturnCategoryDto() {
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto(1L,"1","1");
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);
        CategoryDto result = categoryService.getById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(categoryDto, result);
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void getById_ShouldThrowEntityNotFoundException() {
        Long id = 1L;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(id));
        Assertions.assertEquals("Can't find category by id " + id,
                exception.getMessage());
    }

    @Test
    void save_ShouldReturnCategoryDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto("1","1");
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto(1L,"1","1");

        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.save(requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(categoryDto, result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void update_ShouldReturnUpdatedCategoryDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto("1","1");
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto(1L,"1","1");
        Long id = 1L;

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.update(id, requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(categoryDto, result);
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void update_ShouldThrowEntityNotFoundException() {
        CategoryRequestDto requestDto = new CategoryRequestDto("1","1");
        Long id = 1L;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.update(id, requestDto));
        Assertions.assertEquals("Can't find category by id " + id, exception.getMessage());
    }

    @Test
    void deleteById_ShouldCallDeleteMethod() {
        Long id = 1L;
        doNothing().when(categoryRepository).deleteById(id);

        categoryService.deleteById(id);

        verify(categoryRepository, times(1)).deleteById(id);
    }

    @Test
    void mapCategoryIdsToCategories_ShouldReturnSetOfCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category());

        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        requestDto.setCategoryIds(categoryIds);

        when(categoryRepository.findAllById(categoryIds)).thenReturn(categories);

        Set<Category> result = categoryService.mapCategoryIdsToCategories(requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.containsAll(new HashSet<>(categories)));
    }

    @Test
    void mapCategoryIdsToCategories_ShouldThrowEntityNotFoundException() {
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setCategoryIds(categoryIds);

        when(categoryRepository.findAllById(categoryIds))
                .thenReturn(Collections.singletonList(new Category()));

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.mapCategoryIdsToCategories(requestDto));
        Assertions.assertEquals("One or more categories not found", exception.getMessage());
    }

    @Test
    void mapCategoryIdsToCategories_Update_ShouldReturnSetOfCategories() {
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        List<Category> categories = Arrays.asList(new Category(), new Category());

        UpdateBookRequestDto requestDto = new UpdateBookRequestDto();
        requestDto.setCategoryIds(categoryIds);

        when(categoryRepository.findAllById(categoryIds)).thenReturn(categories);

        Set<Category> result = categoryService.mapCategoryIdsToCategories(requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.containsAll(new HashSet<>(categories)));
    }

    @Test
    void mapCategoryIdsToCategories_Update_ShouldThrowEntityNotFoundException() {
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        UpdateBookRequestDto requestDto = new UpdateBookRequestDto();
        requestDto.setCategoryIds(categoryIds);

        when(categoryRepository.findAllById(categoryIds))
                .thenReturn(Collections.singletonList(new Category()));

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.mapCategoryIdsToCategories(requestDto));
        Assertions.assertEquals("One or more categories not found", exception.getMessage());
    }
}
