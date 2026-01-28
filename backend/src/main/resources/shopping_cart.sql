SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- 創建庫
CREATE DATABASE IF NOT EXISTS shopping_cart;

-- 切換庫
USE shopping_cart;

-- =========================
-- 清除舊表
-- =========================
DROP TABLE IF EXISTS order_item CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS cart_item CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- =========================
-- users
-- =========================
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    avatar VARCHAR(255) DEFAULT '' NOT NULL COMMENT '使用者頭像圖片',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =========================
-- address
-- =========================
CREATE TABLE address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    recipient_name VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    district VARCHAR(255) NOT NULL,
    detail_address VARCHAR(255) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE NOT NULL COMMENT '預設地址條件',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- =========================
-- product
-- =========================
CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0 NOT NULL,
    images VARCHAR(1000),
    category VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =========================
-- cart_item
-- =========================
CREATE TABLE cart_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT DEFAULT 1 NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_cart_product FOREIGN KEY (product_id) REFERENCES product(id)
);
CREATE INDEX idx_cart_user ON cart_item(user_id);
CREATE INDEX idx_cart_product ON cart_item(product_id);

-- =========================
-- orders
-- =========================
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    total_amount DECIMAL(12,2),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '訂單狀態',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_orders_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT chk_orders_status CHECK (status IN ('PENDING','PAID','SHIPPED','FINISHED','CANCELLED'))
);
CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_address ON orders(address_id);

-- =========================
-- order_item
-- =========================
CREATE TABLE order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT DEFAULT 1 NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES product(id)
);
CREATE INDEX idx_order_item_order ON order_item(order_id);
CREATE INDEX idx_order_item_product ON order_item(product_id);

-- =========================
-- 初始資料
-- =========================
INSERT INTO product(name, description, price, stock, images, category) VALUES
('iPhone 15', 'Apple iPhone 15 128GB', 29900.00, 50, 'iphone15_1.jpg,iphone15_2.jpg', '手機'),
('MacBook Air M2', 'Apple MacBook Air M2 13-inch', 39900.00, 20, 'mba_m2_1.jpg', '筆電'),
('AirPods Pro 2', 'Apple AirPods Pro 第二代', 7490.00, 100, 'airpodspro2.jpg', '配件'),
('Samsung Galaxy S23', 'Samsung Galaxy S23 256GB', 26900.00, 35, 'galaxy_s23.jpg', '手機'),
('Sony WH-1000XM5', 'Sony 無線降噪耳機 WH-1000XM5', 11900.00, 60, 'sony_xm5.jpg', '耳機'),
('iPad Air', 'Apple iPad Air 10.9-inch', 19900.00, 40, 'ipadair.jpg', '平板');

INSERT INTO users(username, email, password) VALUES
('test', 'test@gmail.com', 'test123');
