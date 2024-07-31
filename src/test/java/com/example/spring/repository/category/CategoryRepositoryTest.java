package com.example.spring.repository.category;

import com.example.spring.model.Category;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Saves category and then finds category by ID")
    void findCategoryById_ReturnsCategory() {
        Category category = new Category();
        category.setName("Science Fiction");
        category.setDescription("A category for science fiction books.");
        category = categoryRepository.save(category);
        Optional<Category> foundCategory = categoryRepository.findById(category.getId());
        Assertions.assertTrue(foundCategory.isPresent(),
                "Category should be found");
        Assertions.assertEquals(category.getName(), foundCategory.get().getName(),
                "Category name should match");
    }

    @Test
    @DisplayName("Returns empty for non-existent category ID")
    void findByNonExistentId_ReturnsEmpty() {
        Long nonExistentId = 999L;
        Optional<Category> category = categoryRepository.findById(nonExistentId);
        Assertions.assertFalse(category.isPresent(),
                "No category should be found for a non-existent ID");
    }

    @Test
    @DisplayName("Finds all categories by IDs")
    @Sql(scripts = "classpath:database/add-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByIds_ReturnsCategories() {
        List<Category> categories = categoryRepository.findAllById(List.of(1L, 2L));
        Assertions.assertEquals(2, categories.size(),
                "Should return 2 categories");
        Assertions.assertTrue(categories.stream()
                        .anyMatch(cat -> cat.getName().equals("Fiction")),
                "Category 'Fiction' should be found");
        Assertions.assertTrue(categories.stream()
                        .anyMatch(cat -> cat.getName().equals("Non-Fiction")),
                "Category 'Non-Fiction' should be found");
    }
}
