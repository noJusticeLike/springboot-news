package com.springboot.news.service.impl;

import com.springboot.news.exception.ResourceNotFoundException;
import com.springboot.news.model.Article;
import com.springboot.news.payload.ArticleDto;
import com.springboot.news.payload.ArticleResponse;
import com.springboot.news.repository.ArticleRepository;
import com.springboot.news.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;
    private ModelMapper mapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper mapper) {
        this.articleRepository = articleRepository;
        this.mapper = mapper;
    }

    @Override
    public ArticleDto createArticle(ArticleDto articleDto) {
        return null;
    }

    @Override
    public ArticleResponse getAllArticles(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Article> articles = articleRepository.findAll(pageable);

        List<Article> listOfArticles = articles.getContent();
        List<ArticleDto> content = listOfArticles.stream().map(this::mapToDto).collect(Collectors.toList());

        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setContent(content);
        articleResponse.setPageNo(articles.getNumber());
        articleResponse.setPageSize(articles.getSize());
        articleResponse.setTotalElements(articles.getTotalElements());
        articleResponse.setTotalPages(articles.getTotalPages());
        articleResponse.setLast(articles.isLast());

        return articleResponse;
    }

    @Override
    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
        return mapToDto(article);
    }

    @Override
    public ArticleDto updateArticle(ArticleDto articleDto, Long id) {
        return null;
    }

    @Override
    public void deleteArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));

        articleRepository.delete(article);
    }

    @Override
    public List<ArticleDto> getArticlesByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public List<ArticleDto> searchArticles(String query) {
        List<Article> articles = articleRepository.searchArticles(query);
        return articles.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ArticleDto mapToDto(Article article) {
        ArticleDto articleDto = mapper.map(article, ArticleDto.class);
        return articleDto;
    }

    private Article mapToEntity(ArticleDto articleDto) {
        Article article = mapper.map(articleDto, Article.class);
        return article;
    }
}
