package com.example.spring.repository.book.filter.bookprovider;

import com.example.spring.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class IsbnSpecificationProviderTest {
    public static final String ISBN_KEY = "isbn";
    public static final String SAMPLE_ISBN = "978-3-16-148410-0";
    private final IsbnSpecificationProvider provider = new IsbnSpecificationProvider();

    @Test
    @DisplayName("Get the key of the specification provider for ISBN")
    public void testGetKey_ShouldReturnExpectedKey() {
        String key = provider.getKey();
        Assertions.assertEquals(ISBN_KEY, key);
    }

    @Test
    @DisplayName("Get the specification for ISBN")
    public void testGetSpecification_ShouldReturnNonNullSpecification() {
        Specification<Book> spec = provider.getSpecification(SAMPLE_ISBN);
        Assertions.assertNotNull(spec);
    }
}
