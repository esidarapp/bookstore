package com.example.spring.controller;

import com.example.spring.dto.book.BookDto;
import com.example.spring.dto.book.BookDtoWithoutCategoryIds;
import com.example.spring.dto.book.BookSearchParameters;
import com.example.spring.dto.book.CreateBookRequestDto;
import com.example.spring.dto.book.UpdateBookRequestDto;
import com.example.spring.service.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get all books", description = "Get a list of all available books")
    public List<BookDto> findAll(@ParameterObject @PageableDefault Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Find book by id", description = "Find book by id")
    public BookDtoWithoutCategoryIds findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new book", description = "Create a new book")
    public BookDto save(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update book by id", description = "Update book by id")
    public BookDto updateById(@PathVariable Long id,
                              @RequestBody @Valid UpdateBookRequestDto requestDto) {
        return bookService.updateById(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by id", description = "Delete book by id")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Search books by params", description = "Search books by params")
    @GetMapping("/search")
    public List<BookDto> search(
            @ParameterObject @PageableDefault BookSearchParameters searchParameters) {
        return bookService.search(searchParameters);
    }
}
