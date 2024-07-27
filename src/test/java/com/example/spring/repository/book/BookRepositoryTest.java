package com.example.spring.repository.book;

import com.example.spring.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("""
            Finds book by ID
            """)
    @Sql(scripts = "classpath:database/add-kobzar-book-to-books-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-kobzar-book-from-books-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findBookByExistedId_ReturnsBook() {
        Optional<Book> existedBook = bookRepository.findById(1L);
        Assertions.assertTrue(existedBook.isPresent(), "The book should be found");
        Book book = existedBook.get();
        Assertions.assertEquals("Kobzar", book.getTitle(),
                "The book title should match");
        Assertions.assertEquals("Taras Shevchenko", book.getAuthor(),
                "The book author should match");
    }

    @Test
    @DisplayName("Returns empty for non-existent book ID")
    void findByNonExistentId_ReturnsEmpty() {
        Long nonExistentId = 999L;
        Optional<Book> book = bookRepository.findById(nonExistentId);
        Assertions.assertFalse(book.isPresent(), "No book should be found for a non-existent ID");
    }

    @Test
    @DisplayName("Finds all books with pagination")
    @Sql(scripts = "classpath:database/add-kobzar-book-to-books-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-kobzar-book-from-books-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllWithPagination_ReturnsPaginatedBooks() {
        Pageable pageable = Pageable.ofSize(1); // Устанавливаем размер страницы 1
        Page<Book> booksPage = bookRepository.findAll(pageable);
        Assertions.assertEquals(1, booksPage.getSize(), "Page size should be 1");
        Assertions.assertTrue(booksPage.hasContent(), "Page should have content");
        Assertions.assertEquals("Kobzar", booksPage.getContent().get(0).getTitle(),
                "The book title should match");
    }

    @Test
    @DisplayName("Finds books that have a specific category")
    @Sql(scripts = "classpath:database/add-book-with-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-book-with-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findBooksWithCategory_ChecksCategoryInBook() {
        Long categoryId = 1L;
        List<Book> books = bookRepository.findByCategoryId(categoryId);
        Assertions.assertFalse(books.isEmpty(), "Books should be found for the given category ID");
        Assertions.assertTrue(books.stream()
                        .allMatch(book -> book.getCategories().stream()
                                .anyMatch(c -> c.getId().equals(categoryId))),
                "All books should belong to the given category ID");
    }

    @Test
    @DisplayName("Saves a new book and retrieves it")
    void saveBook_SavesAndReturnsBook() {
        Book newBook = new Book();
        newBook.setTitle("New Book");
        newBook.setAuthor("New Author");
        newBook.setIsbn("978-3-16-148410-1");
        newBook.setPrice(BigDecimal.valueOf(300.00));
        newBook.setDescription("A new book description");
        newBook.setCoverImage("http://example.com/newbook.jpg");

        Book savedBook = bookRepository.save(newBook);

        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());
        Assertions.assertTrue(retrievedBook.isPresent(), "The book should be found");
        Book book = retrievedBook.get();
        Assertions.assertEquals("New Book", book.getTitle(),
                "The book title should match");
        Assertions.assertEquals("New Author", book.getAuthor(),
                "The book author should match");
        Assertions.assertEquals("978-3-16-148410-1", book.getIsbn(),
                "The book ISBN should match");
        Assertions.assertEquals(BigDecimal.valueOf(300.00), book.getPrice(),
                "The book price should match");
        Assertions.assertEquals("A new book description", book.getDescription(),
                "The book description should match");
        Assertions.assertEquals("http://example.com/newbook.jpg", book.getCoverImage(),
                "The book cover image URL should match");
    }

}
