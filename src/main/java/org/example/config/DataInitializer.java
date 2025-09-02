package org.example.config;

import org.example.service.ProductService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize admin user
        userService.initializeAdminUser();
        
        // Add some sample products
        try {
            productService.createProduct("Laptop", new BigDecimal("999.99"));
            productService.createProduct("Smartphone", new BigDecimal("599.99"));
            productService.createProduct("Headphones", new BigDecimal("199.99"));
            productService.createProduct("Tablet", new BigDecimal("399.99"));
            productService.createProduct("Wireless Mouse", new BigDecimal("29.99"));
        } catch (Exception e) {
            // Products might already exist, ignore
        }
        
        System.out.println("Data initialization completed!");
        System.out.println("Admin user: admin / admin123");
        System.out.println("Sample products have been created.");
    }
}
