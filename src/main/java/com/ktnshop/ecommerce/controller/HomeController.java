package com.ktnshop.ecommerce.controller;

import com.ktnshop.ecommerce.model.Product;
import com.ktnshop.ecommerce.model.Category;
import com.ktnshop.ecommerce.service.ProductService;
import com.ktnshop.ecommerce.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        List<Product> featuredProducts = productService.getFeaturedProducts();
        List<Product> newProducts = productService.getNewProducts();

        model.addAttribute("categories", categories);
        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("newProducts", newProducts);
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
