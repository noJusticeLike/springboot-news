package com.springboot.news.repository;

import com.springboot.news.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT p FROM Category p WHERE " +
            "p.name LIKE CONCAT('%', :query, '%')" +
            "Or p.description LIKE CONCAT('%', :query, '%')")
    List<Category> searchCategories(String query);
}
