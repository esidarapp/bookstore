package com.example.spring.service.book;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldReturnBookDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Title");
        requestDto.setAuthor("Author");
        requestDto.setIsbn("978-3-16-148410-0");
        requestDto.setPrice(BigDecimal.valueOf(19.99));
        requestDto.setDescription("Description");
        requestDto.setCoverImage("http://example.com/image.jpg");
        requestDto.setCategoryIds(Collections.singletonList(1L));

        Book book = new Book();
        BookDto bookDto = new BookDto();

        when(categoryService.mapCategoryIdsToCategories(any(CreateBookRequestDto.class)))
                .thenReturn(Set.of(new Category()));
        when(bookMapper.toModel(any(CreateBookRequestDto.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.save(requestDto);

        Assertions.assertNotNull(result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void findAll_ShouldReturnListOfBookDto() {
        Book book = new Book();
        BookDto bookDto = new BookDto();
        Page<Book> bookPage = new PageImpl<>(List.of(book));

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        List<BookDto> result = bookService.findAll(Pageable.unpaged());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void findById_ShouldReturnBookDtoWithoutCategoryIds() {
        Book book = new Book();
        BookDtoWithoutCategoryIds bookDtoWithoutCategoryIds = new BookDtoWithoutCategoryIds(
                1L,
                "Title",
                "Author",
                "978-3-16-148410-0",
                BigDecimal.valueOf(19.99),
                "Description",
                "http://example.com/image.jpg");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.toDtoWithoutCategories(any(Book.class)))
                .thenReturn(bookDtoWithoutCategoryIds);

        BookDtoWithoutCategoryIds result = bookService.findById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Title", result.title());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateById_ShouldReturnUpdatedBookDto() {
        UpdateBookRequestDto requestDto = new UpdateBookRequestDto();
        requestDto.setTitle("Updated Title");
        requestDto.setAuthor("Updated Author");
        requestDto.setIsbn("978-3-16-148410-1");
        requestDto.setPrice(BigDecimal.valueOf(29.99));
        requestDto.setDescription("Updated Description");
        requestDto.setCoverImage("http://example.com/updated_image.jpg");
        requestDto.setCategoryIds(Collections.singletonList(2L));

        Book book = new Book();
        BookDto bookDto = new BookDto();

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(categoryService.mapCategoryIdsToCategories(any(UpdateBookRequestDto.class)))
                .thenReturn(Set.of(new Category()));
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.updateById(1L, requestDto);

        Assertions.assertNotNull(result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void deleteById_ShouldCallDeleteMethod() {
        doNothing().when(bookRepository).deleteById(anyLong());

        bookService.deleteById(1L);

        verify(bookRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void search_ShouldReturnListOfBookDto() {
        BookSearchParameters params = new BookSearchParameters(null,null, null);
        Book book = new Book();
        BookDto bookDto = new BookDto();
        List<Book> books = List.of(book);

        when(bookSpecificationBuilder.build(any(BookSearchParameters.class)))
                .thenReturn(Specification.where(null));
        when(bookRepository.findAll(any(Specification.class))).thenReturn(books);
        when(bookMapper.toDtoList(anyList())).thenReturn(List.of(bookDto));

        List<BookDto> result = bookService.search(params);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void getBooksByCategoryId_ShouldReturnListOfBookDto() {
        Book book = new Book();
        BookDto bookDto = new BookDto();
        List<Book> books = List.of(book);

        when(bookRepository.findByCategoryId(anyLong())).thenReturn(books);
        when(bookMapper.toDtoList(anyList())).thenReturn(List.of(bookDto));

        List<BookDto> result = bookService.getBooksByCategoryId(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByCategoryId(anyLong());
    }

    @Test
    void findBookById_ShouldThrowEntityNotFoundException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(1L));
        Assertions.assertEquals("Can't find book by id 1", exception.getMessage());
    }
}
