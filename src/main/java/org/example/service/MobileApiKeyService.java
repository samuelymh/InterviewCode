package org.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service for handling API key authentication for mobile applications
 */
@Service
public class MobileApiKeyService {
    
    @Value("${mobile.api.key:default-mobile-api-key}")
    private String validApiKey;
    
    /**
     * Validate the provided API key
     * @param apiKey The API key to validate
     * @return true if the API key is valid, false otherwise
     */
    public boolean isValidApiKey(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            return false;
        }
        return validApiKey.equals(apiKey.trim());
    }
    
    /**
     * Get the valid API key (for testing purposes)
     * @return The valid API key
     */
    public String getValidApiKey() {
        return validApiKey;
    }
}
