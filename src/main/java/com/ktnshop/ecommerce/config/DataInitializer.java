package com.ktnshop.ecommerce.config;

import com.ktnshop.ecommerce.model.User;
import com.ktnshop.ecommerce.model.Category;
import com.ktnshop.ecommerce.model.Product;
import com.ktnshop.ecommerce.repository.UserRepository;
import com.ktnshop.ecommerce.repository.CategoryRepository;
import com.ktnshop.ecommerce.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, CategoryRepository categoryRepository,
                          ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run(String... args) throws Exception {
        // Initialize Users
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@ktnshop.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.UserRole.ADMIN);
            admin.setIsActive(true);
            userRepository.save(admin);

            User customer = new User();
            customer.setUsername("customer");
            customer.setEmail("customer@ktnshop.com");
            customer.setPassword(passwordEncoder.encode("customer123"));
            customer.setRole(User.UserRole.CUSTOMER);
            customer.setIsActive(true);
            userRepository.save(customer);
        }

        // Initialize Categories
        if (categoryRepository.count() == 0) {
            Category[] categories = {
                new Category(null, "PC Gaming", "pc-gaming", "Máy tính chơi game hiệu suất cao", "https://via.placeholder.com/250/667eea/ffffff", LocalDateTime.now()),
                new Category(null, "Laptop Gaming", "laptop-gaming", "Laptop gaming chuyên dụng", "https://via.placeholder.com/250/764ba2/ffffff", LocalDateTime.now()),
                new Category(null, "Linh kiện", "linh-kien", "Các linh kiện máy tính", "https://via.placeholder.com/250/f093fb/ffffff", LocalDateTime.now()),
                new Category(null, "Màn hình", "man-hinh", "Màn hình display chuyên dùng", "https://via.placeholder.com/250/4facfe/ffffff", LocalDateTime.now()),
                new Category(null, "Phụ kiện Gaming", "phu-kien-gaming", "Phụ kiện chơi game", "https://via.placeholder.com/250/f4511e/ffffff", LocalDateTime.now()),
                new Category(null, "Tai nghe", "tai-nghe", "Tai nghe gaming và audio", "https://via.placeholder.com/250/8e24aa/ffffff", LocalDateTime.now())
            };
            for (Category category : categories) {
                categoryRepository.save(category);
            }
        }

        // Initialize Products
        if (productRepository.count() == 0) {
            Category pcGaming = categoryRepository.findBySlug("pc-gaming").orElse(null);
            Category linhKien = categoryRepository.findBySlug("linh-kien").orElse(null);
            Category laptopGaming = categoryRepository.findBySlug("laptop-gaming").orElse(null);

            if (pcGaming != null) {
                Product p1 = new Product();
                p1.setName("PC Gaming Pro RTX 4090");
                p1.setSlug("pc-gaming-pro-4090");
                p1.setDescription("PC Gaming cao cấp với RTX 4090 và i9-13900K");
                p1.setShortDescription("RTX 4090 | i9-13900K | 32GB RAM | 2TB SSD");
                p1.setOriginalPrice(BigDecimal.valueOf(49990000));
                p1.setSellingPrice(BigDecimal.valueOf(45990000));
                p1.setImage("https://via.placeholder.com/250x250/667eea/ffffff?text=PC+Gaming+Pro");
                p1.setCategory(pcGaming);
                p1.setStockQuantity(5);
                p1.setIsFeatured(true);
                p1.setIsNew(false);
                productRepository.save(p1);

                Product p2 = new Product();
                p2.setName("PC Gaming Ultra RTX 4080");
                p2.setSlug("pc-gaming-ultra-4080");
                p2.setDescription("PC Gaming tầm trung cao RTX 4080 và i7-13700K");
                p2.setShortDescription("RTX 4080 | i7-13700K | 16GB RAM | 1TB SSD");
                p2.setOriginalPrice(BigDecimal.valueOf(36990000));
                p2.setSellingPrice(BigDecimal.valueOf(32990000));
                p2.setImage("https://via.placeholder.com/250x250/764ba2/ffffff?text=PC+Gaming+Ultra");
                p2.setCategory(pcGaming);
                p2.setStockQuantity(8);
                p2.setIsFeatured(true);
                p2.setIsNew(false);
                productRepository.save(p2);
            }

            if (linhKien != null) {
                Product p3 = new Product();
                p3.setName("GPU NVIDIA RTX 4090");
                p3.setSlug("gpu-rtx-4090");
                p3.setDescription("Card đồ họa RTX 4090 24GB GDDR6X");
                p3.setShortDescription("RTX 4090 | 24GB GDDR6X | PCIe 4.0");
                p3.setOriginalPrice(BigDecimal.valueOf(25990000));
                p3.setSellingPrice(BigDecimal.valueOf(22990000));
                p3.setImage("https://via.placeholder.com/250x250/4facfe/ffffff?text=RTX+4090");
                p3.setCategory(linhKien);
                p3.setStockQuantity(3);
                p3.setIsFeatured(true);
                p3.setIsNew(false);
                productRepository.save(p3);
            }

            if (laptopGaming != null) {
                Product p4 = new Product();
                p4.setName("Laptop ASUS ROG Strix G16");
                p4.setSlug("laptop-asus-rog-strix-g16");
                p4.setDescription("Laptop gaming ASUS ROG Strix G16 RTX 4090");
                p4.setShortDescription("RTX 4090 | i9-13900HX | 32GB DDR5 | 4K OLED");
                p4.setOriginalPrice(BigDecimal.valueOf(59990000));
                p4.setSellingPrice(BigDecimal.valueOf(52990000));
                p4.setImage("https://via.placeholder.com/250x250/f4511e/ffffff?text=ASUS+ROG");
                p4.setCategory(laptopGaming);
                p4.setStockQuantity(4);
                p4.setIsFeatured(true);
                p4.setIsNew(true);
                productRepository.save(p4);
            }
        }
    }
}
