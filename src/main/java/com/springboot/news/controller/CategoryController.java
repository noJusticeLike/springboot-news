package com.springboot.news.controller;

import com.springboot.news.payload.CategoryDto;
import com.springboot.news.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "CRUD REST APIs for Category Resource")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Create Category REST API",
            description = "Create Category REST API is used to save category into database"
    )
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Category By Id REST API",
            description = "Get Category By Id REST API is used to get a single category from database"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId) {
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    @Operation(
            summary = "Get All Categories REST API",
            description = "Get All Categories REST API is used to fetch all categories from database"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(
            summary = "Update Category REST API",
            description = "Update Category REST API is used to update a particular category in database"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear Authentication")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("id") Long categoryId) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
    }

    @Operation(
            summary = "Delete Category REST API",
            description = "Delete Category REST API is used to delete a particular category from database"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Категорія успішно видалена");
    }

    @Operation(
            summary = "Search Categories REST API",
            description = "Search Categories REST API is used to fetch all categories by query from database"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/search")
    public ResponseEntity<List<CategoryDto>> searchCategories(@RequestParam("query") String query){
       List<CategoryDto> categoryDtos = categoryService.searchCategories(query);
       return ResponseEntity.ok(categoryDtos);
    }
}
