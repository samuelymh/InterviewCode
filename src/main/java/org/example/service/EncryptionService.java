package org.example.service;

import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptionService {
    
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String ENCRYPTION_KEY = "MySecretKey12345MySecretKey12345"; // 32 bytes for AES-256
    
    private SecretKey secretKey;
    
    public EncryptionService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            SecureRandom secureRandom = new SecureRandom(ENCRYPTION_KEY.getBytes());
            keyGen.init(256, secureRandom); // Use 256 bits for AES-256
            this.secretKey = keyGen.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize encryption service", e);
        }
    }
    
    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }
    
    public String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}
