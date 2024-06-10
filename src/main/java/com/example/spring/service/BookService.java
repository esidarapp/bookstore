package com.example.spring.service;

import com.example.spring.dto.BookDto;
import com.example.spring.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
