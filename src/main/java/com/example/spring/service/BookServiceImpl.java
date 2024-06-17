package com.example.spring.service;

import com.example.spring.dto.BookDto;
import com.example.spring.dto.CreateBookRequestDto;
import com.example.spring.exception.EntityNotFoundException;
import com.example.spring.mapper.BookMapper;
import com.example.spring.model.Book;
import com.example.spring.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book "
                        + "by id " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book "
                        + "by id " + id)
        );
        book.setPrice(requestDto.getPrice());
        book.setAuthor(requestDto.getAuthor());
        book.setIsbn(requestDto.getIsbn());
        book.setTitle(requestDto.getTitle());
        book.setDescription(requestDto.getDescription());
        book.setCoverImage(requestDto.getCoverImage());
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto deleteById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book "
                        + "by id " + id)
        );
        book.setDeleted(true);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }
}
