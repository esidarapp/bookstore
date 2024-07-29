package com.example.spring.repository.book.filter;

import static com.example.spring.repository.book.filter.bookprovider.AuthorSpecificationProviderTest.AUTHOR_KEY;
import static com.example.spring.repository.book.filter.bookprovider.AuthorSpecificationProviderTest.SAMPLE_AUTHOR;
import static com.example.spring.repository.book.filter.bookprovider.IsbnSpecificationProviderTest.ISBN_KEY;
import static com.example.spring.repository.book.filter.bookprovider.IsbnSpecificationProviderTest.SAMPLE_ISBN;
import static com.example.spring.repository.book.filter.bookprovider.TitleSpecificationProviderTest.SAMPLE_TITLE;
import static com.example.spring.repository.book.filter.bookprovider.TitleSpecificationProviderTest.TITLE_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.example.spring.dto.book.BookSearchParameters;
import com.example.spring.model.Book;
import com.example.spring.repository.book.filter.bookprovider.AuthorSpecificationProvider;
import com.example.spring.repository.book.filter.bookprovider.IsbnSpecificationProvider;
import com.example.spring.repository.book.filter.bookprovider.TitleSpecificationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class BookSpecificationBuilderTest {
    @Mock
    private SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Mock
    private TitleSpecificationProvider titleSpecificationProvider;

    @Mock
    private AuthorSpecificationProvider authorSpecificationProvider;

    @Mock
    private IsbnSpecificationProvider isbnSpecificationProvider;

    @InjectMocks
    private BookSpecificationBuilder bookSpecificationBuilder;

    @BeforeEach
    public void setUp() {
        lenient().when(bookSpecificationProviderManager.getSpecificationProvider(TITLE_KEY))
                .thenReturn(titleSpecificationProvider);
        lenient().when(bookSpecificationProviderManager.getSpecificationProvider(AUTHOR_KEY))
                .thenReturn(authorSpecificationProvider);
        lenient().when(bookSpecificationProviderManager.getSpecificationProvider(ISBN_KEY))
                .thenReturn(isbnSpecificationProvider);
    }

    @Test
    @DisplayName("Build specification with all parameters provided")
    public void testBuild_WithAllParameters_ReturnsSpecification() {
        BookSearchParameters searchParameters = new BookSearchParameters(
                SAMPLE_TITLE, SAMPLE_AUTHOR, SAMPLE_ISBN);
        Specification<Book> titleSpec = (root, query, criteriaBuilder) -> null;
        Specification<Book> authorSpec = (root, query, criteriaBuilder) -> null;
        Specification<Book> isbnSpec = (root, query, criteriaBuilder) -> null;

        when(titleSpecificationProvider.getSpecification(SAMPLE_TITLE))
                .thenReturn(titleSpec);
        when(authorSpecificationProvider.getSpecification(SAMPLE_AUTHOR))
                .thenReturn(authorSpec);
        when(isbnSpecificationProvider.getSpecification(SAMPLE_ISBN))
                .thenReturn(isbnSpec);

        Specification<Book> spec = bookSpecificationBuilder.build(searchParameters);

        assertNotNull(spec);
    }

    @Test
    @DisplayName("Build specification with some parameters provided")
    public void testBuild_WithSomeParameters_ReturnsSpecification() {
        BookSearchParameters searchParameters = new BookSearchParameters(
                null, SAMPLE_AUTHOR, null);
        Specification<Book> authorSpec = (root, query, criteriaBuilder) -> null;

        when(authorSpecificationProvider.getSpecification(SAMPLE_AUTHOR)).thenReturn(authorSpec);

        Specification<Book> spec = bookSpecificationBuilder.build(searchParameters);
        assertNotNull(spec);
    }
}
