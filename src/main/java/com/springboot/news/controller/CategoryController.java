package com.springboot.news.controller;

import com.springboot.news.payload.CategoryDto;
import com.springboot.news.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/index";
    }

    @GetMapping("/category")
    public String showCategoryPage(Model model, @RequestParam Long id) {
        try {
            CategoryDto categoryDto = categoryService.getCategory(id);
            model.addAttribute("categoryDto", categoryDto);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/categories";
        }
        return "categories/ViewCategory";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        CategoryDto categoryDto = new CategoryDto();
        model.addAttribute("categoryDto", categoryDto);
        return "categories/CreateCategory";
    }

    @PostMapping("create")
    public String createCategory(
            @Valid @ModelAttribute CategoryDto categoryDto, BindingResult result) {
        if (result.hasErrors()) {
            return "categories/CreateCategory";
        }

        categoryService.addCategory(categoryDto);

        return "redirect:/categories";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam Long id) {
        try {
            CategoryDto categoryDto = categoryService.getCategory(id);
            model.addAttribute("categoryDto", categoryDto);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/categories";
        }
        return "categories/EditCategory";
    }

    @PostMapping("/edit")
    public String updateCategory(Model model, @RequestParam Long id, @Valid @ModelAttribute CategoryDto categoryDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "categories/EditCategory";
            }

            categoryService.updateCategory(categoryDto, id);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/categories";
    }

    @GetMapping("/delete")
    public String deleteCategory(@RequestParam Long id) {
        try {
            categoryService.deleteCategory(id);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/categories";
    }
}
