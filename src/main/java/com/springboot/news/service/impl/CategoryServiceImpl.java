package com.springboot.news.service.impl;

import com.springboot.news.exception.ResourceNotFoundException;
import com.springboot.news.model.Category;
import com.springboot.news.payload.CategoryDto;
import com.springboot.news.repository.CategoryDao;
import com.springboot.news.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;
    private final ModelMapper mapper;

    public CategoryServiceImpl(CategoryDao categoryDao, ModelMapper mapper) {
        this.categoryDao = categoryDao;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Long addCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        return categoryDao.create(category);
    }

    @Override
    @Transactional
    public List<Long> addCategories(List<CategoryDto> categoryDtos) {
        List<Category> categories = categoryDtos.stream()
                .map(categoryDto -> mapper.map(categoryDto, Category.class))
                .toList();

        return categories.stream()
                .map(categoryDao::create)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryDao.read(categoryId);
        if (category == null) {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryDao.readAll();
        return categories.stream().map(category -> mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = categoryDao.read(categoryId);
        if (category == null) {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        categoryDao.update(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryDao.read(categoryId);
        if (category == null) {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
        categoryDao.delete(categoryId);
    }

    @Override
    public List<CategoryDto> searchCategories(String query) {
        List<Category> categories = categoryDao.search(query);
        return categories.stream().map(category -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }
}

