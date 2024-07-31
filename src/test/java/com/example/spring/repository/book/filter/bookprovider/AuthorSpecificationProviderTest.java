package com.example.spring.repository.book.filter.bookprovider;

import com.example.spring.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class AuthorSpecificationProviderTest {
    public static final String AUTHOR_KEY = "author";
    public static final String SAMPLE_AUTHOR = "John Doe";
    private final AuthorSpecificationProvider provider = new AuthorSpecificationProvider();

    @Test
    @DisplayName("Get the key of the specification provider for author")
    public void testGetKey_ShouldReturnExpectedKey() {
        String key = provider.getKey();
        Assertions.assertEquals(AUTHOR_KEY, key);
    }

    @Test
    @DisplayName("Get the specification for author")
    public void testGetSpecification_ShouldReturnNonNullSpecification() {
        Specification<Book> spec = provider.getSpecification(SAMPLE_AUTHOR);
        Assertions.assertNotNull(spec);
    }
}
