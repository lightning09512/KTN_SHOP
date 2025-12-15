package com.ktnshop.ecommerce.controller;

import com.ktnshop.ecommerce.model.Category;
import com.ktnshop.ecommerce.model.Product;
import com.ktnshop.ecommerce.service.CategoryService;
import com.ktnshop.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/{slug}")
    public String getCategoryBySlug(
            @PathVariable String slug,
            Model model) {
        Category category = categoryService.getCategoryBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        List<Product> products = productService.getProductsByCategory(slug);
        List<Category> allCategories = categoryService.getAllCategories();
        
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        model.addAttribute("categories", allCategories);
        return "category-products";
    }

    @GetMapping("/api/{slug}")
    @ResponseBody
    public Category getCategoryApi(@PathVariable String slug) {
        return categoryService.getCategoryBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
