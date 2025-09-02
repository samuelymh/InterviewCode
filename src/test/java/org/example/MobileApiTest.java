package org.example;

import org.example.service.MobileApiKeyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MobileApiTest {
    
    @Autowired
    private MobileApiKeyService mobileApiKeyService;
    
    @Test
    public void testMobileApiKeyService() {
        // Test that the service is properly configured
        assertNotNull(mobileApiKeyService);
        
        // Test that we can get the valid API key
        String validApiKey = mobileApiKeyService.getValidApiKey();
        assertNotNull(validApiKey);
        assertFalse(validApiKey.isEmpty());
        
        // Test that the service correctly validates API keys
        assertTrue(mobileApiKeyService.isValidApiKey(validApiKey));
        assertFalse(mobileApiKeyService.isValidApiKey("invalid-key"));
        assertFalse(mobileApiKeyService.isValidApiKey(null));
        assertFalse(mobileApiKeyService.isValidApiKey(""));
    }
    
    @Test
    public void testMobileApiKeyValidation() {
        String validApiKey = mobileApiKeyService.getValidApiKey();
        
        // Test various scenarios
        assertTrue(mobileApiKeyService.isValidApiKey(validApiKey));
        assertFalse(mobileApiKeyService.isValidApiKey("wrong-key"));
        assertFalse(mobileApiKeyService.isValidApiKey(""));
        assertFalse(mobileApiKeyService.isValidApiKey(null));
        assertFalse(mobileApiKeyService.isValidApiKey("  "));
    }
}
