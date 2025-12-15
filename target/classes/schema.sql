-- Categories
INSERT INTO categories (name, slug, description, image, created_at) VALUES
('PC Gaming', 'pc-gaming', 'Máy tính chơi game hiệu suất cao', 'https://via.placeholder.com/250/667eea/ffffff', NOW()),
('Laptop Gaming', 'laptop-gaming', 'Laptop gaming chuyên dụng', 'https://via.placeholder.com/250/764ba2/ffffff', NOW()),
('Linh kiện', 'linh-kien', 'Các linh kiện máy tính', 'https://via.placeholder.com/250/f093fb/ffffff', NOW()),
('Màn hình', 'man-hinh', 'Màn hình display chuyên dùng', 'https://via.placeholder.com/250/4facfe/ffffff', NOW()),
('Phụ kiện Gaming', 'phu-kien-gaming', 'Phụ kiện chơi game', 'https://via.placeholder.com/250/f4511e/ffffff', NOW()),
('Tai nghe', 'tai-nghe', 'Tai nghe gaming và audio', 'https://via.placeholder.com/250/8e24aa/ffffff', NOW());

-- Products (PC Gaming category)
INSERT INTO products (name, slug, description, short_description, original_price, selling_price, image, category_id, stock_quantity, is_featured, is_new, created_at, updated_at) VALUES
('PC Gaming Pro RTX 4090', 'pc-gaming-pro-4090', 'PC Gaming cao cấp với RTX 4090 và i9-13900K', 'RTX 4090 | i9-13900K | 32GB RAM | 2TB SSD', 49990000, 45990000, 'https://via.placeholder.com/250x250/667eea/ffffff?text=PC+Gaming+Pro', 1, 5, 1, 0, NOW(), NOW()),
('PC Gaming Ultra RTX 4080', 'pc-gaming-ultra-4080', 'PC Gaming tầm trung cao RTX 4080 và i7-13700K', 'RTX 4080 | i7-13700K | 16GB RAM | 1TB SSD', 36990000, 32990000, 'https://via.placeholder.com/250x250/764ba2/ffffff?text=PC+Gaming+Ultra', 1, 8, 1, 0, NOW(), NOW()),
('PC Gaming Budget RTX 4070', 'pc-gaming-budget-4070', 'PC Gaming giá rẻ với RTX 4070 và i5-13600K', 'RTX 4070 | i5-13600K | 16GB RAM | 512GB SSD', 24990000, 21990000, 'https://via.placeholder.com/250x250/f093fb/ffffff?text=PC+Gaming+Budget', 1, 12, 0, 1, NOW(), NOW());

-- Products (Linh kiện category)
INSERT INTO products (name, slug, description, short_description, original_price, selling_price, image, category_id, stock_quantity, is_featured, is_new, created_at, updated_at) VALUES
('GPU NVIDIA RTX 4090', 'gpu-rtx-4090', 'Card đồ họa RTX 4090 24GB GDDR6X', 'RTX 4090 | 24GB GDDR6X | PCIe 4.0', 25990000, 22990000, 'https://via.placeholder.com/250x250/4facfe/ffffff?text=RTX+4090', 3, 3, 1, 0, NOW(), NOW()),
('CPU Intel i9-13900K', 'cpu-i9-13900k', 'Bộ xử lý Intel Core i9-13900K 3.0GHz 36MB', 'i9-13900K | 3.0GHz | 36MB Cache', 14990000, 12990000, 'https://via.placeholder.com/250x250/667eea/ffffff?text=i9-13900K', 3, 7, 1, 0, NOW(), NOW()),
('RAM Kingston DDR5 32GB', 'ram-kingston-ddr5-32gb', 'RAM Kingston FURY DDR5 32GB 6000MHz', 'DDR5 | 32GB | 6000MHz', 8990000, 6990000, 'https://via.placeholder.com/250x250/764ba2/ffffff?text=RAM+DDR5', 3, 15, 0, 1, NOW(), NOW()),
('SSD Samsung 990 Pro 2TB', 'ssd-samsung-990-pro-2tb', 'SSD Samsung 990 Pro 2TB NVMe M.2 PCIe 4.0', 'NVMe | 2TB | 7100MB/s', 7990000, 5990000, 'https://via.placeholder.com/250x250/f093fb/ffffff?text=SSD+2TB', 3, 10, 0, 0, NOW(), NOW());

-- Products (Laptop Gaming category)
INSERT INTO products (name, slug, description, short_description, original_price, selling_price, image, category_id, stock_quantity, is_featured, is_new, created_at, updated_at) VALUES
('Laptop ASUS ROG Strix G16', 'laptop-asus-rog-strix-g16', 'Laptop gaming ASUS ROG Strix G16 RTX 4090', 'RTX 4090 | i9-13900HX | 32GB DDR5 | 4K OLED', 59990000, 52990000, 'https://via.placeholder.com/250x250/f4511e/ffffff?text=ASUS+ROG', 2, 4, 1, 1, NOW(), NOW()),
('Laptop MSI Raider GE78', 'laptop-msi-raider-ge78', 'Laptop MSI Raider GE78 RTX 4080 Super', 'RTX 4080 Super | i9-13900HX | 32GB DDR5 | 17.3 FHD', 54990000, 48990000, 'https://via.placeholder.com/250x250/8e24aa/ffffff?text=MSI+Raider', 2, 3, 1, 0, NOW(), NOW());
