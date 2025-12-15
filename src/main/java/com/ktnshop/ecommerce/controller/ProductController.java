package com.ktnshop.ecommerce.controller;

import com.ktnshop.ecommerce.model.Product;
import com.ktnshop.ecommerce.model.Category;
import com.ktnshop.ecommerce.service.ProductService;
import com.ktnshop.ecommerce.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.searchProducts("", pageable);
        
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products";
    }

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.searchProducts(q, pageable);
        
        model.addAttribute("products", products);
        model.addAttribute("searchQuery", q);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "search-results";
    }

    @GetMapping("/{slug}")
    public String getProductBySlug(@PathVariable String slug, Model model) {
        Product product = productService.getProductBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product-detail";
    }

    @PostMapping("/api/search")
    @ResponseBody
    public List<Product> apiSearchProducts(@RequestParam String q) {
        return productService.searchProducts(q, PageRequest.of(0, 10)).getContent();
    }
}
