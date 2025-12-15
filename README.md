# KTNShop - E-Commerce Platform Hoàn Chỉnh

Một nền tảng thương mại điện tử hiện đại được xây dựng bằng Spring Boot 3.2, cung cấp đầy đủ chức năng mua bán online.

## ✨ Tính Năng Chính

### 👤 Quản Lý Người Dùng
- ✅ Đăng ký tài khoản mới với xác thực
- ✅ Đăng nhập/Đăng xuất
- ✅ Quản lý thông tin cá nhân
- ✅ Lịch sử đơn hàng
- ✅ Đổi mật khẩu

### 🛒 Giỏ Hàng & Thanh Toán
- ✅ Thêm/xóa sản phẩm vào giỏ hàng
- ✅ Cập nhật số lượng sản phẩm
- ✅ Xóa toàn bộ giỏ hàng
- ✅ Tính toán tổng tiền tự động
- ✅ Xem chi tiết giỏ hàng

### 📦 Quản Lý Đơn Hàng
- ✅ Tạo đơn hàng từ giỏ hàng
- ✅ Xem chi tiết đơn hàng
- ✅ Theo dõi trạng thái đơn hàng
- ✅ Hủy đơn hàng (nếu chưa giao)
- ✅ Lịch sử đơn hàng của người dùng

### 🏪 Quản Lý Sản Phẩm
- ✅ Danh sách sản phẩm theo danh mục
- ✅ Tìm kiếm sản phẩm
- ✅ Xem chi tiết sản phẩm
- ✅ Sản phẩm bán chạy
- ✅ Sản phẩm mới
- ✅ Xem nhanh sản phẩm

### 📊 Danh Mục Sản Phẩm
- ✅ Lọc sản phẩm theo danh mục
- ✅ Hiển thị danh mục cấp 1

## 🏗️ Kiến Trúc Hệ Thống

```
KTNShop/
├── src/main/java/com/ktnshop/ecommerce/
│   ├── model/                 # Entity Models
│   │   ├── User.java
│   │   ├── Customer.java
│   │   ├── Product.java
│   │   ├── Category.java
│   │   ├── Cart.java
│   │   ├── CartItem.java
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   └── Payment.java
│   │
│   ├── repository/            # Data Access Layer
│   │   ├── UserRepository.java
│   │   ├── ProductRepository.java
│   │   ├── CartRepository.java
│   │   ├── CartItemRepository.java
│   │   ├── OrderRepository.java
│   │   └── ...
│   │
│   ├── service/               # Business Logic Layer
│   │   ├── AuthService.java
│   │   ├── ProductService.java
│   │   ├── CartService.java
│   │   ├── OrderService.java
│   │   ├── CategoryService.java
│   │   └── PaymentService.java
│   │
│   ├── controller/            # Web Layer
│   │   ├── HomeController.java
│   │   ├── AuthController.java
│   │   ├── ProductController.java
│   │   ├── CartController.java
│   │   ├── OrderController.java
│   │   ├── CategoryController.java
│   │   ├── AccountController.java
│   │   └── PaymentController.java
│   │
│   └── config/                # Configuration
│       ├── SecurityConfig.java
│       └── DataInitializer.java
│
├── src/main/resources/
│   ├── templates/             # Thymeleaf Templates
│   │   ├── index.html
│   │   ├── login.html
│   │   ├── register.html
│   │   ├── product-detail.html
│   │   ├── cart.html
│   │   ├── checkout.html
│   │   ├── order-success.html
│   │   ├── my-orders.html
│   │   ├── order-detail.html
│   │   ├── account.html
│   │   ├── category-products.html
│   │   └── fragments/
│   │       └── layout.html
│   │
│   ├── static/
│   │   ├── css/
│   │   │   └── style.css
│   │   └── js/
│   │       └── script.js
│   │
│   ├── application.properties
│   └── schema.sql
│
└── pom.xml
```

## 🚀 Hướng Dẫn Chạy

### Yêu Cầu Tiên Quyết
- Java 17+
- Maven 3.8+
- MySQL 5.7+

### Cài Đặt Cơ Sở Dữ Liệu
```sql
CREATE DATABASE ktnshop_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ktnshop_db;
```

### Cấu Hình Application
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/ktnshop_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Chạy Ứng Dụng
```bash
# Build project
mvn clean install

# Chạy ứng dụng
mvn spring-boot:run
```

Truy cập: `http://localhost:8080`

## 📱 Giao Diện Chính

### Trang Chủ
- Banner quảng cáo
- Sản phẩm bán chạy
- Sản phẩm mới
- Sidebar danh mục
- Footer thông tin

### Trang Đăng Nhập/Đăng Ký
- Form đăng nhập
- Form đăng ký (7 trường)
- Xác thực mật khẩu
- Lỗi validation

### Trang Giỏ Hàng
- Danh sách sản phẩm
- Cập nhật số lượng
- Xóa sản phẩm
- Tính toán tổng tiền
- Nút thanh toán

### Trang Thanh Toán
- Thông tin khách hàng
- Địa chỉ giao hàng
- Phương thức thanh toán
- Xem lại đơn hàng

### Trang Đơn Hàng
- Danh sách đơn hàng
- Chi tiết đơn hàng
- Trạng thái đơn hàng
- Hủy đơn hàng

### Trang Tài Khoản
- Thông tin cá nhân
- Đơn hàng gần đây
- Chỉnh sửa thông tin
- Đổi mật khẩu

## 🔒 Bảo Mật

- ✅ Mã hóa mật khẩu (BCrypt)
- ✅ Session quản lý người dùng
- ✅ Kiểm tra quyền truy cập
- ✅ Validation input

## 📊 Cơ Sở Dữ Liệu

### Các Bảng Chính
- `users` - Người dùng hệ thống
- `customers` - Thông tin khách hàng
- `products` - Sản phẩm bán
- `categories` - Danh mục sản phẩm
- `carts` - Giỏ hàng
- `cart_items` - Chi tiết giỏ hàng
- `orders` - Đơn hàng
- `order_items` - Chi tiết đơn hàng
- `payments` - Thanh toán

## 🎯 Trạng Thái Đơn Hàng

- `PENDING` - Chờ xác nhận
- `CONFIRMED` - Đã xác nhận
- `SHIPPING` - Đang giao
- `DELIVERED` - Đã giao
- `CANCELLED` - Đã hủy

## 💡 Tính Năng Mở Rộng (Tương Lai)

- [ ] Tích hợp thanh toán online (VNPay, MoMo)
- [ ] Hệ thống đánh giá & bình luận
- [ ] Mã giảm giá/Voucher
- [ ] Yêu thích sản phẩm
- [ ] So sánh sản phẩm
- [ ] Chat hỗ trợ khách hàng
- [ ] Admin Dashboard
- [ ] Email notification
- [ ] SMS notification
- [ ] Tích hợp Facebook/Google login

## 📝 Thông Tin Liên Hệ

- Hotline: 1900 1234
- Email: support@ktnshop.vn
- Website: https://ktnshop.vn

## 📄 License

MIT License - Tự do sử dụng cho mục đích thương mại và cá nhân.

---

**Được phát triển bởi KTN Team** ❤️
