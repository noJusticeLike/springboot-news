package com.springboot.news.service;

import com.springboot.news.payload.ArticleDto;
import com.springboot.news.payload.ArticleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto);

    ArticleResponse getAllArticles(int pageNo, int pageSize, String sortBy, String sortDir);

    ArticleDto getArticleById(Long id);

    ArticleDto updateArticle(ArticleDto articleDto, Long id);

    List<ArticleDto> updateArticles(List<ArticleDto> articlesDto);

    void deleteArticleById(Long id);

    List<ArticleDto> getArticlesByCategory(Long categoryId);

    List<ArticleDto> searchArticles(String query);

    List<ArticleDto> findByTitleOrContent(String query);

    List<ArticleDto> getArticlesAfterDate(LocalDate date);
}
