package com.ktnshop.ecommerce.controller;

import com.ktnshop.ecommerce.model.*;
import com.ktnshop.ecommerce.service.CartService;
import com.ktnshop.ecommerce.service.ProductService;
import com.ktnshop.ecommerce.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final CategoryService categoryService;

    public CartController(CartService cartService, ProductService productService, CategoryService categoryService) {
        this.cartService = cartService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        Cart cart = cartService.getOrCreateCart(customer);
        
        // Tính tổng tiền
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
            for (CartItem item : cart.getCartItems()) {
                BigDecimal itemTotal = item.getProduct().getSellingPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                totalPrice = totalPrice.add(itemTotal);
            }
        }
        
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("isEmpty", cart.getCartItems() == null || cart.getCartItems().isEmpty());
        model.addAttribute("categories", categoryService.getAllCategories());
        
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        Cart cart = cartService.getOrCreateCart(customer);
        cartService.addToCart(cart, productId, quantity);
        
        redirectAttributes.addFlashAttribute("successMessage", 
            "✓ Đã thêm '" + product.getName() + "' vào giỏ hàng!");
        
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(
            @RequestParam Long cartItemId,
            @RequestParam Integer quantity,
            HttpSession session) {
        
        if (quantity > 0) {
            cartService.updateCartItemQuantity(cartItemId, quantity);
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(
            @RequestParam Long cartItemId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        cartService.removeFromCart(cartItemId);
        redirectAttributes.addFlashAttribute("infoMessage", "Đã xóa sản phẩm khỏi giỏ hàng");
        
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
