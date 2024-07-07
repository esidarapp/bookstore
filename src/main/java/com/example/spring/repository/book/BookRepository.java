package com.example.spring.repository.book;

import com.example.spring.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {
    @Query("SELECT DISTINCT b "
            + "FROM Book b "
            + "JOIN b.categories c "
            + "WHERE c.id = :categoryId AND c.isDeleted = false AND b.isDeleted = false")
    @EntityGraph(value = "Book.categories")
    List<Book> findByCategoryId(@Param("categoryId") Long categoryId);

    @Override
    @EntityGraph(value = "Book.categories")
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(value = "Book.categories")
    @Override
    Optional<Book> findById(Long id);
}
