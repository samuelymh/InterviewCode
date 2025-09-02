package org.example.dto;

import org.example.entity.Product;
import org.example.service.ProductService;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price; // decrypted price
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public ProductDTO() {
    }
    
    public ProductDTO(Long id, String name, BigDecimal price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Static method to convert Product entity to ProductDTO
    public static ProductDTO fromProduct(Product product, ProductService productService) {
        try {
            BigDecimal decryptedPrice = productService.getDecryptedPrice(product);
            return new ProductDTO(
                product.getId(),
                product.getName(),
                decryptedPrice,
                product.getCreatedAt(),
                product.getUpdatedAt()
            );
        } catch (Exception e) {
            // If decryption fails, return the product with a default price
            System.err.println("Failed to decrypt price for product " + product.getId() + ": " + e.getMessage());
            return new ProductDTO(
                product.getId(),
                product.getName(),
                BigDecimal.ZERO, // Default to zero if decryption fails
                product.getCreatedAt(),
                product.getUpdatedAt()
            );
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
