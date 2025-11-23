CREATE DATABASE IF NOT EXISTS ecommerce;
USE ecommerce;

CREATE TABLE IF NOT EXISTS category (
  category_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS product (
  product_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  description TEXT,
  price DECIMAL(10,2) NOT NULL,
  stock INT DEFAULT 0,
  category_id INT,
  image_path VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS user_table (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  full_name VARCHAR(150),
  email VARCHAR(150) UNIQUE,
  role ENUM('CUSTOMER','ADMIN') DEFAULT 'CUSTOMER',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders (
  order_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  total_amount DECIMAL(12,2) NOT NULL,
  status VARCHAR(50) DEFAULT 'PLACED',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_item (
  order_item_id INT AUTO_INCREMENT PRIMARY KEY,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

INSERT INTO category (name, description) VALUES ('Electronics','Phones, laptops, gadgets') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO category (name, description) VALUES ('Fashion','Clothing & accessories') ON DUPLICATE KEY UPDATE name=name;

INSERT INTO product (name, description, price, stock, category_id) VALUES
('Smartphone ABC','5.5 inch display, 64GB', 9999.00, 50, 1)
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO user_table (username, password_hash, full_name, email, role) VALUES
('admin', 'REPLACE_WITH_HASH', 'Admin User','admin@example.com','ADMIN')
ON DUPLICATE KEY UPDATE username=username;
