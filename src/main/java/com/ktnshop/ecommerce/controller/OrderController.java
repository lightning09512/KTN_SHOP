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
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CategoryService categoryService;

    public OrderController(OrderService orderService, CategoryService categoryService) {
        this.orderService = orderService;
        this.categoryService = categoryService;
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("customer", customer);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "checkout";
    }

    @PostMapping("/create")
    public String createOrder(
            @RequestParam String shippingAddress,
            @RequestParam(required = false) String notes,
            HttpSession session,
            Model model) {
        Customer customer = (Customer) session.getAttribute("currentCustomer");
        if (customer == null) {
            return "redirect:/auth/login";
        }

        try {
            Order order = orderService.createOrderFromCart(customer, shippingAddress, notes);
            return "redirect:/orders/success/" + order.getId();
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("customer", customer);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "checkout";
        }
    }

    @GetMapping("/success/{orderId}")
    public String orderSuccess(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

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
                .orElseThrow(() -> new RuntimeException("Order not found"));

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
                .orElseThrow(() -> new RuntimeException("Order not found"));

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
