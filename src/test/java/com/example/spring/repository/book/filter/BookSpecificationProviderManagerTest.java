package com.example.spring.repository.book.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.spring.model.Book;
import com.example.spring.repository.book.filter.bookprovider.AuthorSpecificationProvider;
import com.example.spring.repository.book.filter.bookprovider.TitleSpecificationProvider;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookSpecificationProviderManagerTest {

    @Mock
    private TitleSpecificationProvider titleSpecificationProvider;

    @Mock
    private AuthorSpecificationProvider authorSpecificationProvider;

    @Test
    @DisplayName("Get specification provider by key")
    public void testGetSpecificationProvider_ShouldReturnProvider() {
        when(titleSpecificationProvider.getKey()).thenReturn("title");
        SpecificationProvider<Book> titleProvider = new BookSpecificationProviderManager(
                List.of(titleSpecificationProvider)
        ).getSpecificationProvider("title");
        assertEquals(titleSpecificationProvider, titleProvider);
    }

    @Test
    @DisplayName("Get specification provider by key when provider is not found")
    public void testGetSpecificationProvider_ShouldThrowException() {
        when(authorSpecificationProvider.getKey()).thenReturn("author");
        BookSpecificationProviderManager manager = new BookSpecificationProviderManager(
                List.of(authorSpecificationProvider)
        );

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                manager.getSpecificationProvider("nonexistent_key")
        );
        assertEquals("Can't find correct specification provider for key nonexistent_key",
                thrown.getMessage());
    }
}
