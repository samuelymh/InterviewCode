package org.example.service;

import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private EncryptionService encryptionService;
    
    public List<Product> getAllProducts() {
        return productRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public Product createProduct(String name, BigDecimal price) {
        Product product = new Product();
        product.setName(name);
        
        // Encrypt the price before saving
        String encryptedPrice = encryptionService.encrypt(price.toString());
        product.setPrice(encryptedPrice);
        
        return productRepository.save(product);
    }
    
    public Product updateProduct(Long id, String name, BigDecimal price) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        Product product = existingProduct.get();
        product.setName(name);
        
        // Encrypt the updated price
        String encryptedPrice = encryptionService.encrypt(price.toString());
        product.setPrice(encryptedPrice);
        
        return productRepository.save(product);
    }
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    public BigDecimal getDecryptedPrice(Product product) {
        try {
            String decryptedPrice = encryptionService.decrypt(product.getPrice());
            return new BigDecimal(decryptedPrice);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt price", e);
        }
    }
}
