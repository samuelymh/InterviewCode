package org.example;

import org.example.service.EncryptionService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EncryptionTest {
    
    @Test
    public void testEncryptionDecryption() {
        EncryptionService encryptionService = new EncryptionService();
        
        String originalPrice = "999.99";
        String encryptedPrice = encryptionService.encrypt(originalPrice);
        String decryptedPrice = encryptionService.decrypt(encryptedPrice);
        
        assertNotNull(encryptedPrice);
        assertNotEquals(originalPrice, encryptedPrice);
        assertEquals(originalPrice, decryptedPrice);
        
        System.out.println("Original: " + originalPrice);
        System.out.println("Encrypted: " + encryptedPrice);
        System.out.println("Decrypted: " + decryptedPrice);
    }
}
