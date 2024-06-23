package com.example.spring.repository.filter.book;

import com.example.spring.model.Book;
import com.example.spring.repository.filter.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    private static final String FIELD_TITLE = "title";

    @Override
    public String getKey() {
        return FIELD_TITLE;
    }

    @Override
    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> root.get(FIELD_TITLE).in(param);
    }
}
