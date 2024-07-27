package com.example.spring.repository.book.filter.bookprovider;

import com.example.spring.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class TitleSpecificationProviderTest {
    private final TitleSpecificationProvider provider = new TitleSpecificationProvider();

    @Test
    @DisplayName("Get the key of the specification provider for title")
    public void testGetKey_ShouldReturnExpectedKey() {
        String key = provider.getKey();
        Assertions.assertEquals("title", key);
    }

    @Test
    @DisplayName("Get the specification for title")
    public void testGetSpecification_ShouldReturnNonNullSpecification() {
        String title = "Some Book Title";
        Specification<Book> spec = provider.getSpecification(title);
        Assertions.assertNotNull(spec);
    }
}
