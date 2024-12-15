package com.springboot.news.service;

import com.springboot.news.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    Long addCategory(CategoryDto categoryDto);

    List<Long> addCategories(List<CategoryDto> categoryDtos);

    CategoryDto getCategory(Long categoryId);

    List<CategoryDto> getAllCategories();

    void updateCategory(CategoryDto categoryDto, Long categoryId);

    void deleteCategory(Long categoryId);

    List<CategoryDto> searchCategories(String query);
}