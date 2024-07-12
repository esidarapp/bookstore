package com.example.spring.service.book;

import com.example.spring.dto.book.BookDto;
import com.example.spring.dto.book.BookDtoWithoutCategoryIds;
import com.example.spring.dto.book.BookSearchParameters;
import com.example.spring.dto.book.CreateBookRequestDto;
import com.example.spring.dto.book.UpdateBookRequestDto;
import com.example.spring.exception.EntityNotFoundException;
import com.example.spring.mapper.BookMapper;
import com.example.spring.model.Book;
import com.example.spring.model.Category;
import com.example.spring.repository.book.BookRepository;
import com.example.spring.repository.book.filter.BookSpecificationBuilder;
import com.example.spring.service.category.CategoryService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryService categoryService;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Set<Category> categories = categoryService.mapCategoryIdsToCategories(requestDto);
        Book book = bookMapper.toModel(requestDto);
        book.setCategories(categories);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDtoWithoutCategoryIds findById(Long id) {
        Book book = findBookById(id);
        return bookMapper.toDtoWithoutCategories(book);
    }

    @Override
    public BookDto updateById(Long id, UpdateBookRequestDto requestDto) {
        Set<Category> categories = categoryService.mapCategoryIdsToCategories(requestDto);
        Book book = findBookById(id);
        bookMapper.updateBookFromDto(requestDto, book);
        book.setCategories(categories);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParameters params) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        List<Book> books = bookRepository.findAll(bookSpecification);
        return bookMapper.toDtoList(books);
    }

    @Override
    public List<BookDto> getBooksByCategoryId(Long categoryId) {
        return bookMapper.toDtoList(bookRepository.findByCategoryId(categoryId));
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id " + id)
        );
    }

}
