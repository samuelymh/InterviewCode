package org.example.controller;

import org.example.dto.MobileProductDTO;
import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.service.MobileApiKeyService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private MobileApiKeyService mobileApiKeyService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = products.stream()
            .map(product -> ProductDTO.fromProduct(product, productService))
            .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> ProductDTO.fromProduct(product, productService))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            Product product = productService.createProduct(
                productRequest.getName(), 
                productRequest.getPrice()
            );
            
            ProductDTO productDTO = ProductDTO.fromProduct(product, productService);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Product created successfully");
            response.put("product", productDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to create product: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        try {
            Product product = productService.updateProduct(
                id, 
                productRequest.getName(), 
                productRequest.getPrice()
            );
            
            ProductDTO productDTO = ProductDTO.fromProduct(product, productService);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Product updated successfully");
            response.put("product", productDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update product: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Product deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to delete product: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        List<ProductDTO> productDTOs = products.stream()
            .map(product -> ProductDTO.fromProduct(product, productService))
            .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    // ===== MOBILE API ENDPOINTS =====
    
    /**
     * Get all products in mobile-friendly JSON format
     * Returns: {"name": "product name", "price": "product price"}
     */
    @GetMapping("/mobile")
    public ResponseEntity<?> getAllProductsMobile(@RequestHeader(value = "X-API-Key", required = false) String apiKey) {
        if (!mobileApiKeyService.isValidApiKey(apiKey)) {
            Map<String, String> error = Map.of(
                "error", "Invalid or missing API key",
                "message", "Access denied"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        
        List<Product> products = productService.getAllProducts();
        List<MobileProductDTO> mobileProducts = products.stream()
            .map(this::convertToMobileFormat)
            .collect(Collectors.toList());
        return ResponseEntity.ok(mobileProducts);
    }
    
    /**
     * Get a specific product by ID in mobile-friendly JSON format
     */
    @GetMapping("/mobile/{id}")
    public ResponseEntity<?> getProductByIdMobile(@PathVariable Long id, 
                                                 @RequestHeader(value = "X-API-Key", required = false) String apiKey) {
        if (!mobileApiKeyService.isValidApiKey(apiKey)) {
            Map<String, String> error = Map.of(
                "error", "Invalid or missing API key",
                "message", "Access denied"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        
        return productService.getProductById(id)
                .map(this::convertToMobileFormat)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Health check endpoint for mobile API
     */
    @GetMapping("/mobile/health")
    public ResponseEntity<?> healthCheckMobile(@RequestHeader(value = "X-API-Key", required = false) String apiKey) {
        if (!mobileApiKeyService.isValidApiKey(apiKey)) {
            Map<String, String> error = Map.of(
                "error", "Invalid or missing API key",
                "message", "Access denied"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        
        Map<String, String> health = Map.of(
            "status", "healthy",
            "message", "Mobile API is running"
        );
        return ResponseEntity.ok(health);
    }
    
    /**
     * Convert Product entity to mobile-friendly JSON format
     * Returns: {"name": "product name", "price": "product price"}
     */
    private MobileProductDTO convertToMobileFormat(Product product) {
        try {
            String decryptedPrice = productService.getDecryptedPrice(product).toString();
            return new MobileProductDTO(product.getName(), decryptedPrice);
        } catch (Exception e) {
            // If decryption fails, return with a default price
            System.err.println("Failed to decrypt price for product " + product.getId() + ": " + e.getMessage());
            return new MobileProductDTO(product.getName(), "0.00");
        }
    }
    
    // Inner class for product request
    public static class ProductRequest {
        private String name;
        private BigDecimal price;
        
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
    }
}
