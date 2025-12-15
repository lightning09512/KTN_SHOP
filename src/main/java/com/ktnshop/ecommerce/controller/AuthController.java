package com.ktnshop.ecommerce.controller;

import com.ktnshop.ecommerce.model.User;
import com.ktnshop.ecommerce.model.Customer;
import com.ktnshop.ecommerce.service.AuthService;
import com.ktnshop.ecommerce.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final CategoryService categoryService;

    public AuthController(AuthService authService, CategoryService categoryService) {
        this.authService = authService;
        this.categoryService = categoryService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        try {
            User user = authService.authenticate(username, password);
            session.setAttribute("currentUser", user);
            
            // Fetch customer if exists
            Customer customer = authService.getCustomerByUserId(user.getId());
            if (customer != null) {
                session.setAttribute("currentCustomer", customer);
            }
            
            return "redirect:/";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phoneNumber,
            Model model) {
        try {
            if (!password.equals(confirmPassword)) {
                model.addAttribute("error", "Mật khẩu không khớp");
                model.addAttribute("categories", categoryService.getAllCategories());
                return "register";
            }

            User user = authService.register(username, email, password);
            
            // Create customer profile
            Customer customer = new Customer();
            customer.setUser(user);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setCity("");
            customer.setCountry("");
            customer.setZipCode("");
            authService.saveCustomer(customer);

            return "redirect:/auth/login?success=Đăng ký thành công. Vui lòng đăng nhập";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
