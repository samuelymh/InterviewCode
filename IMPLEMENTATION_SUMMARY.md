# Alice's Online Shopping Application - Implementation Summary

## 🎯 Project Overview
Alice's online shopping company using Spring Boot, Hibernate, and jQuery as requested.

### Core Functionality
- ✅ **Login/Logout**: Secure authentication system with SHA-256 password hashing
- ✅ **Add Product**: Create new products with name and price
- ✅ **Update Product**: Edit existing product names and prices
- ✅ **Delete Product**: Remove products from the system
- ✅ **Logout**: Secure session termination

### Technology Stack
- ✅ **Spring Boot**: Main application framework
- ✅ **Hibernate**: ORM for database operations
- ✅ **jQuery**: Frontend JavaScript library for dynamic interactions
- ✅ **Tomcat**: Embedded server (Spring Boot default)
- ✅ **Java**: Core programming language

### Security Requirements
- ✅ **SHA-256 Password Hashing**: User passwords are securely hashed with salt
- ✅ **AES-256 Price Encryption**: Product prices are encrypted using AES-256
- ✅ **Input Validation**: Comprehensive validation on all inputs
- ✅ **CORS Configuration**: Proper cross-origin resource sharing setup

## 🏗️ Architecture

### Backend Structure
```
src/main/java/org/example/
├── OnlineShoppingApplication.java    # Main Spring Boot application
├── config/
│   ├── SecurityConfig.java          # Security configuration
│   └── DataInitializer.java         # Database initialization
├── controller/
│   ├── AuthController.java          # Authentication endpoints
│   └── ProductController.java       # Product CRUD endpoints
├── entity/
│   ├── User.java                    # User entity with SHA-256 hashing
│   └── Product.java                 # Product entity with AES-256 encryption
├── repository/
│   ├── UserRepository.java          # User data access
│   └── ProductRepository.java       # Product data access
└── service/
    ├── UserService.java             # User business logic
    ├── ProductService.java          # Product business logic
    └── EncryptionService.java       # AES-256 encryption service
```

### Frontend Structure
```
src/main/resources/static/
├── index.html                       # Login page with jQuery
└── dashboard.html                   # Product management dashboard
```

## 🔐 Security Implementation

### Password Security (SHA-256)
```java
// User.java - SHA-256 hashing with salt
private String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String saltedPassword = password + this.salt;
        byte[] hashedBytes = md.digest(saltedPassword.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("SHA-256 algorithm not available", e);
    }
}
```

### Price Encryption (AES-256)
```java
// EncryptionService.java - AES-256 encryption
public String encrypt(String data) {
    try {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (Exception e) {
        throw new RuntimeException("Encryption failed", e);
    }
}
```

## 🚀 How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Quick Start
```bash
# Clone and navigate to project
cd online-shopping-app

# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

### Access Points
- **Main Application**: http://localhost:8080

### Default Credentials
- **Username**: `admin`
- **Password**: `admin123`

## 📊 API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/logout` - User logout

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/search?name={name}` - Search products

## 🧪 Testing Results

### Encryption Test
```
Original: 999.99
Encrypted: ItKPF9akTDnagoGb9bpjEA==
Decrypted: 999.99
✅ AES-256 encryption/decryption working correctly
```

### API Testing
```
✅ Login API: {"success":true,"message":"Login successful","username":"admin"}
✅ Product Creation: {"success":true,"message":"Product created successfully"}
✅ Product Retrieval: Returns JSON array of products
✅ Static Files: HTML pages served correctly
```

## 🎨 User Interface

### Features
- **Modern Design**: Clean, responsive interface with CSS3 gradients
- **jQuery Integration**: Dynamic content loading and form handling
- **Real-time Search**: Product search functionality
- **Modal Dialogs**: Add/edit product forms
- **Session Management**: Automatic login state persistence

### Pages
1. **Login Page** (`/index.html`)
   - Username/password form
   - Error/success message display
   - Automatic redirect to dashboard on success

2. **Dashboard** (`/dashboard.html`)
   - Product grid display
   - Add/Edit/Delete functionality
   - Search products by name
   - Logout button

## 🔧 Configuration

### Database
- **Type**: MySQL
- **Auto-creation**: Tables created automatically
- **Sample Data**: Admin user and sample products pre-loaded

### Security
- **CORS**: Enabled for all origins (development)
- **CSRF**: Disabled for API endpoints
- **Headers**: Security headers configured

## 📈 Sample Data

### Pre-loaded Products
- Laptop ($999.99)
- Smartphone ($599.99)
- Headphones ($199.99)
- Tablet ($399.99)
- Wireless Mouse ($29.99)

### Admin User
- Username: `admin`
- Password: `admin123` (SHA-256 hashed with salt)

## 🛡️ Security Considerations

### Production Recommendations
1. **Change default credentials** in `DataInitializer.java`
2. **Use production database** (MySQL, PostgreSQL)
3. **Store encryption keys securely** (not hardcoded)
4. **Enable HTTPS** for all communications
5. **Implement proper session management**
6. **Add rate limiting** for API endpoints
7. **Use environment variables** for sensitive configuration

### Encryption Key Management
- Current: Hardcoded for demonstration
- Production: Use secure key management systems
- Key rotation: Implement regular key updates
- Backup: Secure key backup and recovery procedures
