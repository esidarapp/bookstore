package com.example.spring.repository.book.filter.bookprovider;

import com.example.spring.model.Book;
import com.example.spring.repository.book.filter.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String FIELD_ISBN = "isbn";

    @Override
    public String getKey() {
        return FIELD_ISBN;
    }

    @Override
    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> root.get(FIELD_ISBN).in(param);
    }
}
