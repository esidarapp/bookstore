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
    private final IsbnSpecificationProvider provider = new IsbnSpecificationProvider();

    @Test
    @DisplayName("Get the key of the specification provider for ISBN")
    public void testGetKey_ShouldReturnExpectedKey() {
        String key = provider.getKey();
        Assertions.assertEquals("isbn", key);
    }

    @Test
    @DisplayName("Get the specification for ISBN")
    public void testGetSpecification_ShouldReturnNonNullSpecification() {
        String isbn = "978-3-16-148410-0";
        Specification<Book> spec = provider.getSpecification(isbn);
        Assertions.assertNotNull(spec);
    }
}
