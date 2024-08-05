# Bookshop API 📚

Welcome to the Bookshop API, a comprehensive backend service for managing books, categories, and user orders in an online bookshop. This project leverages a variety of technologies and follows best practices in software development to deliver a robust and secure API.

## Introduction

The Bookshop API was inspired by the need for a scalable and maintainable backend solution for an online bookstore. It addresses common challenges such as secure user authentication, role-based access control, and efficient data management. Whether you're building a new bookshop platform or enhancing an existing one, this API serves as a solid foundation.

## Technologies Used

- **Spring Boot** 🌱: For building the RESTful web services.
- **Spring Security** 🔒: To handle authentication and authorization.
- **Spring Data JPA** 💾: For database operations.
- **MySQL** 🗄️: As the main database.
- **Swagger** 📜: For API documentation.
- **Docker** 🐳: To containerize the application.
- **MapStruct** 🔄: For object mapping.
- **JUnit & MockMvc** 🧪: For unit and integration testing.

## Features

### User Management 👥

- **Registration**: Users can register with an email, password, and shipping address.
- **Authentication**: Secure login with JWT-based authentication.
- **Role-Based Access Control**: Different permissions for users and admins.

### Book Management 📚

- **CRUD Operations**: Create, read, update, and delete books.
- **Category Assignment**: Assign categories to books.
- **Search**: Search for books by various criteria.

### Order Management 🛒

- **Shopping Cart**: Users can add books to their cart.
- **Order Creation**: Create orders from the shopping cart.
- **Order Tracking**: Track the status of orders.

### API Documentation 📖

- **Swagger**: Interactive API documentation is available at `/swagger-ui.html`.

## Getting Started 🚀

### Prerequisites

- **Java 17**
- **Maven**
- **Docker**

### Setup

1. **Clone the repository**:
   ```sh
   git clone https://github.com/esidarapp/bookstore
2. **Navigate to the project directory**:
   ```sh
   cd bookstore
3. **Build the project**:
   ```sh
   mvn clean install
4. **Start the application using Docker Compose**:
   ```sh
   docker-compose up

### Connecting to a Custom Database 🗄️
To connect to a custom MySQL database, update the application.properties file with your database details:
```properties
spring.datasource.url=jdbc:mysql://<YOUR_DB_HOST>:<YOUR_DB_PORT>/<YOUR_DB_NAME>
spring.datasource.username=<YOUR_DB_USERNAME>
spring.datasource.password=<YOUR_DB_PASSWORD>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

### Entity Relationships
Here's a diagram representing the relationships between the entities in the Bookshop API:
User
├── Role
├── Order
│   ├── OrderItem
│   │   └── Book
│   └── ShoppingCart
│       └── CartItem
│           └── Book
└── Book
└── Category



## Conclusion

The Bookshop API provides a robust foundation for managing users, books, categories, and orders in an online bookstore. By leveraging modern technologies and best practices, it ensures scalability, security, and maintainability. Feel free to explore the API and adapt it to your specific needs. Happy coding!
