package com.example.spring.service.book;

import com.example.spring.dto.book.BookDto;
import com.example.spring.dto.book.BookSearchParameters;
import com.example.spring.dto.book.CreateBookRequestDto;
import com.example.spring.exception.EntityNotFoundException;
import com.example.spring.mapper.BookMapper;
import com.example.spring.model.Book;
import com.example.spring.repository.book.BookRepository;
import com.example.spring.repository.book.filter.BookSpecificationBuilder;
import java.util.List;
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

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
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
    public BookDto findById(Long id) {
        Book book = findBookById(id);
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto requestDto) {
        Book book = findBookById(id);
        bookMapper.updateBookFromDto(requestDto, book);
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

    private Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id " + id)
        );
    }
}
