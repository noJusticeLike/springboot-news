package com.springboot.news.controller;

import com.springboot.news.payload.ArticleDto;
import com.springboot.news.payload.CategoryDto;
import com.springboot.news.service.ArticleService;
import com.springboot.news.service.CategoryService;
import com.springboot.news.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private ArticleService articleService;
    private CategoryService categoryService;

    public ArticleController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<ArticleDto> articles = articleService.getAllArticles(AppConstants.DEFAULT_PAGE_NUMBER, AppConstants.DEFAULT_PAGE_SIZE, AppConstants.DEFAULT_SORT_BY, AppConstants.DEFAULT_SORT_DIRECTION).getContent();
        model.addAttribute("articles", articles);
        return "articles/index";
    }

    @GetMapping("/article")
    public String showArticlePage(Model model, @RequestParam Long id) {
        try {
            ArticleDto articleDto = articleService.getArticleById(id);
            model.addAttribute("articleDto", articleDto);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/articles";
        }
        return "articles/ViewArticle";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        ArticleDto articleDto = new ArticleDto();
        model.addAttribute("articleDto", articleDto);
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "articles/CreateArticle";
    }

    @PostMapping("/create")
    public String createArticle(
            @Valid @ModelAttribute ArticleDto articleDto, BindingResult result) {
        if (result.hasErrors()) {
            return "articles/CreateArticle";
        }

        articleService.createArticle(articleDto);

        return "redirect:/articles";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam Long id) {
        try {
            ArticleDto articleDto = articleService.getArticleById(id);
            model.addAttribute("articleDto", articleDto);
            List<CategoryDto> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/articles";
        }
        return "articles/EditArticle";
    }

    @PostMapping("/edit")
    public String updateArticle(@RequestParam Long id, @Valid @ModelAttribute ArticleDto articleDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "articles/EditArticle";
            }

            articleService.updateArticle(articleDto, id);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/articles";
    }

    @GetMapping("/delete")
    public String deleteArticle(@RequestParam Long id) {
        try {
            articleService.deleteArticleById(id);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/articles";
    }
}
