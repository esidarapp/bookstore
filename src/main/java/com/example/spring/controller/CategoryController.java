package com.example.spring.controller;

import com.example.spring.dto.book.BookDto;
import com.example.spring.dto.category.CategoryDto;
import com.example.spring.dto.category.CategoryRequestDto;
import com.example.spring.service.book.BookService;
import com.example.spring.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create a new category", description = "Create a new category")
    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid CategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get all categories", description = "Get a list of all categories")
    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.findAll();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get category by id", description = "Get category by its id")
    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update category by id", description = "Update category by its id")
    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable Long id,
                                      @RequestBody @Valid CategoryRequestDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete category by id", description = "Delete category by its id")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get books by category id",
            description = "Get a list of books by category id")
    @GetMapping("/{id}/books")
    public List<BookDto> getBooksByCategoryId(@PathVariable Long id) {
        return bookService.getBooksByCategoryId(id);
    }
}
