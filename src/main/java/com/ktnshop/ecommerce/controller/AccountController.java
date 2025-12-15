package com.ktnshop.ecommerce.controller;

import com.ktnshop.ecommerce.model.*;
import com.ktnshop.ecommerce.service.OrderService;
import com.ktnshop.ecommerce.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final OrderService orderService;
    private final CategoryService categoryService;

    public AccountController(OrderService orderService, CategoryService categoryService) {
        this.orderService = orderService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String account(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        List<Order> recentOrders = orderService.getOrdersByCustomer(customer.getId());
        if (recentOrders.size() > 5) {
            recentOrders = recentOrders.subList(0, 5);
        }

        model.addAttribute("recentOrders", recentOrders);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "account";
    }

    @GetMapping("/edit")
    public String editProfile(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("customer", customer);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "account-edit";
    }

    @GetMapping("/change-password")
    public String changePassword(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("categories", categoryService.getAllCategories());
        return "change-password";
    }
}
