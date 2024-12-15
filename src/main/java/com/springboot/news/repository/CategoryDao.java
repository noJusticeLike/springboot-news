package com.springboot.news.repository;

import com.springboot.news.model.Category;

import java.util.List;

public interface CategoryDao {
    Long create(Category category);

    Category read(Long id);

    List<Category> readAll();

    void update(Category category);

    void delete(Long id);

    List<Category> search(String query);
}
