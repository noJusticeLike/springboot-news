package com.springboot.news.controller;

import com.springboot.news.payload.ArticleDto;
import com.springboot.news.payload.ArticleResponse;
import com.springboot.news.service.ArticleService;
import com.springboot.news.utils.AppConstants;
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
@RequestMapping("/api/articles")
@Tag(name = "CRUD REST APIs for Article Resource")
public class ArticleController {
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

   @Operation(
           summary = "Create Article REST API",
           description = "Create Article REST API is used to save article into database"
   )
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@Valid @RequestBody ArticleDto articleDto) {
       ArticleDto savedArticle = articleService.createArticle(articleDto);
        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
   }

    @Operation(
            summary = "Get All Articles REST API",
            description = "Get All Articles REST API is used to fetch all articles from database"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping
    public ArticleResponse getAllArticles(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return articleService.getAllArticles(pageNo, pageSize, sortBy, sortDir);
    }

    @Operation(
            summary = "Get Article By Id REST API",
            description = "Get Article By Id REST API is used to get a single article from database"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    @Operation(
            summary = "Update Article REST API",
            description = "Update Article REST API is used to update a particular article in database"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@Valid @RequestBody ArticleDto articleDto, @PathVariable(name = "id") Long id) {
        ArticleDto articleResponse = articleService.updateArticle(articleDto, id);
        return new ResponseEntity<>(articleResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Article REST API",
            description = "Delete Article REST API is used to delete a particular article from database"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable(name = "id") Long id) {
        articleService.deleteArticleById(id);
        return ResponseEntity.ok("Стаття успішно видалена");
    }

    @Operation(
            summary = "Get Articles By Category REST API",
            description = "Get Articles By Category REST API is used to fetch all articles by category from database"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ArticleDto>> getArticlesByCategory(@PathVariable("id") Long categoryId) {
        List<ArticleDto> articleDtos = articleService.getArticlesByCategory(categoryId);
        return ResponseEntity.ok(articleDtos);
    }

    @Operation(
            summary = "Search Articles REST API",
            description = "Search Articles REST API is used to fetch all articles by query from database"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/search")
    public ResponseEntity<List<ArticleDto>> searchArticles(@RequestParam("query") String query) {
        List<ArticleDto> articleDtos = articleService.searchArticles(query);
        return ResponseEntity.ok(articleDtos);
    }
}
