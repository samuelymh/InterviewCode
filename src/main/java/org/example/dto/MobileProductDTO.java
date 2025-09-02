package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Mobile-specific DTO that matches the required JSON structure:
 * {"name": "product name", "price": "product price"}
 */
public class MobileProductDTO {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("price")
    private String price;
    
    public MobileProductDTO() {
    }
    
    public MobileProductDTO(String name, String price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "MobileProductDTO{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
