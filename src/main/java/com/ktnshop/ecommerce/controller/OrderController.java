package com.ktnshop.ecommerce.controller;

import com.ktnshop.ecommerce.model.*;
import com.ktnshop.ecommerce.service.OrderService;
import com.ktnshop.ecommerce.service.CartService;
import com.ktnshop.ecommerce.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final CategoryService categoryService;

    public OrderController(OrderService orderService, CartService cartService, CategoryService categoryService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.categoryService = categoryService;
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        Cart cart = cartService.getOrCreateCart(customer);
        
        // Kiểm tra giỏ hàng trống
        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return "redirect:/cart";
        }
        
        // Tính tổng tiền
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem item : cart.getCartItems()) {
            BigDecimal itemTotal = item.getProduct().getSellingPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);
        }

        model.addAttribute("customer", customer);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "checkout";
    }

    @PostMapping("/place")
    public String placeOrder(
            @RequestParam String shippingAddress,
            @RequestParam(required = false) String notes,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        // Validation
        if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            Cart cart = cartService.getOrCreateCart(customer);
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (CartItem item : cart.getCartItems()) {
                BigDecimal itemTotal = item.getProduct().getSellingPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                totalPrice = totalPrice.add(itemTotal);
            }
            
            model.addAttribute("customer", customer);
            model.addAttribute("cart", cart);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "checkout";
        }

        try {
            Order order = orderService.createOrderFromCart(customer, shippingAddress, notes);
            redirectAttributes.addFlashAttribute("orderId", order.getId());
            return "redirect:/orders/success/" + order.getId();
        } catch (RuntimeException e) {
            Cart cart = cartService.getOrCreateCart(customer);
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (CartItem item : cart.getCartItems()) {
                BigDecimal itemTotal = item.getProduct().getSellingPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                totalPrice = totalPrice.add(itemTotal);
            }
            
            model.addAttribute("customer", customer);
            model.addAttribute("cart", cart);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("errorMessage", "Lỗi khi tạo đơn hàng: " + e.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "checkout";
        }
    }

    @GetMapping("/success/{orderId}")
    public String orderSuccess(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        model.addAttribute("order", order);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "order-success";
    }

    @GetMapping("/my-orders")
    public String myOrders(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        List<Order> orders = orderService.getOrdersByCustomer(customer.getId());
        model.addAttribute("orders", orders);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "my-orders";
    }

    @GetMapping("/{orderId}")
    public String orderDetail(@PathVariable Long orderId, HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        if (!order.getCustomer().getId().equals(customer.getId())) {
            return "redirect:/orders/my-orders";
        }

        model.addAttribute("order", order);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "order-detail";
    }

    @PostMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        if (!order.getCustomer().getId().equals(customer.getId())) {
            return "redirect:/orders/my-orders";
        }

        if (order.getStatus() == Order.OrderStatus.PENDING || 
            order.getStatus() == Order.OrderStatus.CONFIRMED) {
            orderService.updateOrderStatus(orderId, Order.OrderStatus.CANCELLED);
        }

        return "redirect:/orders/" + orderId;
    }
}
