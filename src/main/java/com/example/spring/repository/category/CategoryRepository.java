package com.example.spring.repository.category;

import com.example.spring.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllById(Iterable<Long> ids);
}
