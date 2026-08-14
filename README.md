# Library Management System

This is a REST API project developed with Spring Boot for managing a library system.

The project allows creating, updating, deleting and viewing authors, books and members. It also supports borrowing and returning books.

## Technologies

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Gradle
- Liquibase
- Lombok
- Swagger / OpenAPI
- Groovy & Spock
- Spring Security
- JWT (JJWT)

## Project Structure

The project follows a layered architecture:

  src/main/java
  ├── config
  ├── controller
  ├── dto
  ├── entity
  ├── enums
  ├── exceptions
  ├── mapper
  ├── repository
  ├── security
  └── service

Entity objects are not returned directly. DTO classes are used for requests and responses.

## Features

- Author CRUD
- Book CRUD
- Member CRUD
- Pagination & Sorting
- Input Validation
- Global Exception Handling
- Swagger Documentation
- Unit Tests
- User Registration & Login
- JWT Authentication
- Spring Security
- Role-Based Authorization (USER / ADMIN)
- Custom 401 & 403 Error Responses

## How to Run

### 1. Clone the repository

```bash
git clone https://github.com/elviragafarova/library-management-system-jwt.git
```

### 2. Create PostgreSQL database

Example:

```
library_management_system
```

### 3. Configure application.yml

Update your database credentials.

Example:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

### 4. Run the project

Run the main Spring Boot application.

Liquibase will automatically create the database tables.

## Swagger

After starting the application, open:

```
http://localhost:1133/swagger-ui/index.html
```

## Validation

Validation is implemented using annotations such as:

- @NotBlank
- @NotNull
- @Size
- @Positive
- @Pattern
- @Email

## Exception Handling

Global exception handling is implemented using `@RestControllerAdvice`.

Custom exceptions:

- AuthorNotFoundException
- BookNotFoundException
- MemberNotFoundException
- BookAlreadyBorrowedException
- BookAlreadyReturnedException
- EmailAlreadyExistsException
- InvalidCredentialsException

Security responses:

- 401 Unauthorized
- 403 Forbidden

## Authentication & Security

The application uses JWT-based authentication and Spring Security.

- JWT tokens contain user email and role information
- Passwords are encrypted using BCrypt
- Protected endpoints require a valid JWT token
- Expired tokens are automatically rejected
- Access is controlled using USER and ADMIN roles

## Testing

Unit tests were written for the service layer using Groovy and Spock Framework.

Covered scenarios:

- Author Service tests
- JWT token generation
- JWT token expiration validation
- Expired token handling