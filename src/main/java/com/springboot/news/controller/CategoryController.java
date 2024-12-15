package com.springboot.news.controller;

import com.springboot.news.payload.CategoryDto;
import com.springboot.news.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "CRUD REST APIs for Category Resource")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Create Category",
            description = "This API creates a new category in the database"
    )
    @ApiResponse(responseCode = "201", description = "Category successfully created")
    @PostMapping
    public ResponseEntity<Long> createCategory(@RequestBody CategoryDto categoryDto) {
        Long categoryId = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(categoryId, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Create Categories",
            description = "This API creates news categories in the database"
    )
    @ApiResponse(responseCode = "201", description = "Categories successfully created")
    @PostMapping("/multiple")
    public ResponseEntity<List<Long>> createCategories(@RequestBody List<CategoryDto> categoryDtos) {
        List<Long> categoryIds = categoryService.addCategories(categoryDtos);
        return new ResponseEntity<>(categoryIds, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Category by ID",
            description = "This API retrieves a category by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Category retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }

    @Operation(
            summary = "Get All Categories",
            description = "This API retrieves all categories from the database"
    )
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(
            summary = "Update Category",
            description = "This API updates an existing category by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Category updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Delete Category",
            description = "This API deletes a category by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Category deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Search Categories",
            description = "This API searches for categories by a query string"
    )
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<List<CategoryDto>> searchCategories(@RequestParam String query) {
        List<CategoryDto> categories = categoryService.searchCategories(query);
        return ResponseEntity.ok(categories);
    }
}

