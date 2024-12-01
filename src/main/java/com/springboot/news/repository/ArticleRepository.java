package com.springboot.news.repository;

import com.springboot.news.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Article p WHERE " +
            "p.title LIKE CONCAT('%', :query, '%')" +
            "Or p.content LIKE CONCAT('%', :query, '%')")
    List<Article> searchArticles(String query);
}
