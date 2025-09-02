# Alice's Online Shopping Application

A secure web application for Alice's online shopping company built with Spring Boot, Hibernate, and jQuery.

## Features

- **Secure Authentication**: User login/logout with password encryption
- **Product Management**: Add, view, and manage products
- **Modern UI**: Responsive design with jQuery and CSS3
- **Database Integration**: MySQL with Hibernate ORM
- **RESTful API**: JSON-based communication
- **Mobile API**: Secure RESTful web service for mobile applications

## Technology Stack

- **Backend**: Spring Boot 3.2.0, Spring Security, Spring Data JPA
- **Database**: MySQL 8.0 with Hibernate ORM
- **Frontend**: HTML5, jQuery 3.6.0, CSS3
- **Build Tool**: Maven 3.9.11
- **Java Version**: 17

## Quick Start

1. **Prerequisites**:
   - Java 17 or higher
   - MySQL 8.0
   - Maven 3.6+

2. **Database Setup**:
   ```sql
   CREATE DATABASE shoppingdb;
   ```

3. **Configuration**:
   - Update `application.properties` with your MySQL credentials
   - The application will automatically create the admin user on startup

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the Application**:
   - Login: http://localhost:8080/
   - Dashboard: http://localhost:8080/dashboard.html

## Mobile API

The application includes a secure RESTful web service specifically designed for mobile applications:

### Endpoints
- `GET /api/products/mobile` - Get all products
- `GET /api/products/mobile/{id}` - Get product by ID
- `GET /api/products/mobile/search?name={name}` - Search products
- `GET /api/products/mobile/health` - Health check

### Authentication
All mobile API endpoints require an API key in the `X-API-Key` header:
```
X-API-Key: alice-mobile-api-key-2024-secure
```

### JSON Response Format
```json
{
  "name": "product name",
  "price": "product price"
}
```

For detailed mobile API documentation, see [MOBILE_API_DOCUMENTATION.md](MOBILE_API_DOCUMENTATION.md)

## Default Credentials

- **Username**: admin
- **Password**: admin123
