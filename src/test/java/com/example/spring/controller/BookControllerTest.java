package com.example.spring.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.spring.dto.book.BookDto;
import com.example.spring.dto.book.BookDtoWithoutCategoryIds;
import com.example.spring.dto.book.CreateBookRequestDto;
import com.example.spring.dto.book.UpdateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:database/add-categories.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:database/delete-categories.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
    ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        teardown(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/add-3-default-books.sql")
            );
        }
    }

    @AfterAll
    static void afterAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/delete-all-books.sql")
            );
        }
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create a new book")
    void save_ValidRequestDto_Success() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("1984");
        requestDto.setAuthor("George Orwell");
        requestDto.setDescription("A dystopian social science fiction novel and cautionary tale.");
        requestDto.setPrice(BigDecimal.valueOf(15.99));
        requestDto.setCoverImage("http://example.com/1984-cover.jpg");
        requestDto.setIsbn("978-3-16-148410-0");
        requestDto.setCategoryIds(List.of(1L, 2L));

        BookDto expected = new BookDto();
        expected.setTitle(requestDto.getTitle());
        expected.setAuthor(requestDto.getAuthor());
        expected.setDescription(requestDto.getDescription());
        expected.setPrice(requestDto.getPrice());
        expected.setCoverImage(requestDto.getCoverImage());
        expected.setIsbn(requestDto.getIsbn());
        expected.setCategoryIds(requestDto.getCategoryIds());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all books")
    void getAll_GivenBooksInCatalog_ShouldReturnAllBooks() throws Exception {
        BookDto book1 = new BookDto();
        book1.setAuthor("Harper Lee");
        book1.setTitle("To Kill a Mockingbird");
        book1.setDescription("A novel about the serious issues of rape and racial inequality.");
        book1.setPrice(BigDecimal.valueOf(10.99));
        book1.setId(1L);
        book1.setCoverImage("http://example.com/mockingbird-cover.jpg");
        book1.setIsbn("978-0-06-112008-4");
        book1.setCategoryIds(List.of(1L,2L));

        BookDto book2 = new BookDto();
        book2.setAuthor("Jane Austen");
        book2.setTitle("Pride and Prejudice");
        book2.setDescription("A novel that charts the development of the protagonist.");
        book2.setPrice(BigDecimal.valueOf(8.99));
        book2.setId(2L);
        book2.setCoverImage("http://example.com/pride-cover.jpg");
        book2.setIsbn("978-1-85326-000-1");
        book2.setCategoryIds(List.of(1L,2L));

        BookDto book3 = new BookDto();
        book3.setAuthor("F. Scott Fitzgerald");
        book3.setTitle("The Great Gatsby");
        book3.setDescription("A story about the Jazz Age in the United States.");
        book3.setPrice(BigDecimal.valueOf(12.99));
        book3.setId(3L);
        book3.setCoverImage("http://example.com/gatsby-cover.jpg");
        book3.setIsbn("978-0-7432-7356-5");
        book3.setCategoryIds(List.of(1L,2L));
        List<BookDto> expected = new ArrayList<>();
        expected.add(book1);
        expected.add(book2);
        expected.add(book3);

        MvcResult result = mockMvc.perform(
                        get("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), BookDto[].class);
        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());

    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Find book by id")
    void findById_ValidId_ShouldReturnBook() throws Exception {
        MvcResult result = mockMvc.perform(get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDtoWithoutCategoryIds actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDtoWithoutCategoryIds.class);

        Assertions.assertNotNull(actual.id(), "ID should not be null");
        Assertions.assertEquals(1L, actual.id());
        Assertions.assertEquals("To Kill a Mockingbird", actual.title());
        Assertions.assertEquals("Harper Lee", actual.author());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update book by id")
    void updateById_ValidRequestDto_Success() throws Exception {
        UpdateBookRequestDto requestDto = new UpdateBookRequestDto();
        requestDto.setTitle("1984 - Updated");
        requestDto.setAuthor("George Orwell");
        requestDto.setIsbn("978-3-16-244430-1");
        requestDto.setPrice(BigDecimal.valueOf(17.99));
        requestDto.setDescription("Updated description.");
        requestDto.setCoverImage("http://example.com/1984-cover-updated.jpg");
        requestDto.setCategoryIds(List.of(1L, 2L));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/books/1")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        Assertions.assertEquals("1984 - Updated", actual.getTitle());
        Assertions.assertEquals("George Orwell", actual.getAuthor());
        Assertions.assertEquals("978-3-16-244430-1", actual.getIsbn());
        Assertions.assertEquals(BigDecimal.valueOf(17.99), actual.getPrice());
        Assertions.assertEquals("Updated description.", actual.getDescription());
        Assertions.assertEquals("http://example.com/1984-cover-updated.jpg",
                actual.getCoverImage());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete book by id")
    void deleteById_ValidId_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/books/3")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Search books")
    void search_ValidParameters_ShouldReturnBooks() throws Exception {
        MvcResult result = mockMvc.perform(get("/books/search")
                        .param("author", "Harper Lee")
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), BookDto[].class);

        Assertions.assertEquals(1, actual.length);
        Assertions.assertEquals("To Kill a Mockingbird", actual[0].getTitle());
        Assertions.assertEquals("Harper Lee", actual[0].getAuthor());
    }
}
