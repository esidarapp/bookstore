package com.example.spring;

import com.example.spring.model.Book;
import com.example.spring.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("Spring Boot in Action");
            book.setAuthor("Craig Walls");
            book.setIsbn("978-1617292545");
            book.setPrice(new BigDecimal("39.99"));
            book.setDescription("Comprehensive guide to Spring Boot");
            book.setCoverImage("spring_boot_in_action.jpg");
            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }
}
