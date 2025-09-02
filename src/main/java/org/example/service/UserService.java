package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User createUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true);
        
        return userRepository.save(user);
    }
    
    public User updateUser(Long id, String username, String password) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        User user = existingUser.get();
        user.setUsername(username);
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }
        
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public boolean authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndActiveTrue(username);
        if (user.isEmpty()) {
            return false;
        }
        
        return user.get().verifyPassword(password);
    }
    
    public void initializeAdminUser() {
        if (!userRepository.existsByUsername("admin")) {
            User adminUser = new User("admin", "admin123");
            adminUser.setActive(true);
            userRepository.save(adminUser);
        }
    }
}
