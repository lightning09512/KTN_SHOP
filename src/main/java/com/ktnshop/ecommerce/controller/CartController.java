package com.ktnshop.ecommerce.controller;

import com.ktnshop.ecommerce.model.*;
import com.ktnshop.ecommerce.service.CartService;
import com.ktnshop.ecommerce.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CategoryService categoryService;

    public CartController(CartService cartService, CategoryService categoryService) {
        this.cartService = cartService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        Cart cart = cartService.getOrCreateCart(customer);
        model.addAttribute("cart", cart);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            HttpSession session) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        Cart cart = cartService.getOrCreateCart(customer);
        cartService.addToCart(cart, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(
            @RequestParam Long cartItemId,
            @RequestParam Integer quantity) {
        cartService.updateCartItemQuantity(cartItemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        Cart cart = cartService.getOrCreateCart(customer);
        cartService.clearCart(cart.getId());
        return "redirect:/cart";
    }
}
