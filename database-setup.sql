-- MySQL Database Setup for Alice's Online Shopping Application
-- Run this script to create the database and tables

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS shoppingdb
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE shoppingdb;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create products table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price VARCHAR(500) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_active ON users(active);
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_created_at ON products(created_at);

-- Insert default admin user (password: admin123)
-- Note: This is a sample user. In production, change the password!
INSERT INTO users (username, password, salt, active) VALUES 
('admin', 'hashed_password_will_be_generated_by_application', 'salt_will_be_generated_by_application', true)
ON DUPLICATE KEY UPDATE active = true;

-- Show table structure
DESCRIBE users;
DESCRIBE products;

-- Show sample data
SELECT 'Users table:' as info;
SELECT id, username, active, created_at FROM users;

SELECT 'Products table:' as info;
SELECT id, name, price, created_at FROM products;
