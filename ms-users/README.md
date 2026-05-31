# MS-Users - User Authentication and Management Microservice

Microservice responsible for user authentication, role and permission management, and user profile management for the **WorldCupTicket** platform.

## 📋 Features

- **User Authentication**: Secure login system with JWT
- **Role Management**: Role and permission control
- **User Profile**: User information management
- **Eureka Integration**: Automatic registration in Service Discovery
- **Prometheus Monitoring**: Metrics and actuators exposed
- **Flyway**: Database version control
- **Spring Security**: Complete application security

## 🛠️ Technologies

- **Java 21**
- **Spring Boot 3.3.0**
- **Spring Cloud 2023.0.0**
- **PostgreSQL 16+**
- **Maven 3.9+**
- **JWT (JJWT 0.12.5)**
- **Lombok**

## 📦 Project Structure

```
ms-users/
├── src/
│   ├── main/
│   │   ├── java/com/worldcupticket/msusers/
│   │   │   ├── config/              # Configurations (Security, JWT, etc.)
│   │   │   ├── controller/          # REST Controllers
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── entity/              # JPA Entities
│   │   │   ├── exception/           # Exception Handlers
│   │   │   ├── mapper/              # Entity-DTO Mappers
│   │   │   ├── repository/          # Spring Data JPA Repositories
│   │   │   ├── security/            # Security (JWT, Filters)
│   │   │   ├── service/             # Service Interfaces
│   │   │   ├── service/impl/        # Service Implementations
│   │   │   └── MsUsersApplication.java  # Main Application Class
│   │   └── resources/
│   │       ├── application.yml      # Main Configuration
│   │       ├── application-dev.yml  # Development Configuration
│   │       └── db/migration/        # Flyway Scripts
│   └── test/
├── pom.xml
└── README.md
```

## 🚀 Quick Start

### Prerequisites

- Java 21 installed
- Maven 3.9 or higher
- PostgreSQL 16 or higher
- Eureka Server running on `http://localhost:8761`

### Local Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ms-users
   ```

2. **Create PostgreSQL database**
   ```sql
   CREATE DATABASE users_db;
   CREATE USER postgres WITH PASSWORD 'postgres';
   ALTER ROLE postgres WITH CREATEDB;
   ```

3. **Configure environment variables** (optional)
   ```bash
   export SERVER_PORT=8081
   export DB_URL=jdbc:postgresql://localhost:5432/users_db
   export DB_USER=postgres
   export DB_PASS=postgres
   export JWT_SECRET=your-secret-key-here
   export EUREKA_URL=http://localhost:8761/eureka
   ```

4. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

5. **Verify availability**
   ```bash
   curl http://localhost:8081/api/v1/health
   ```

## 📝 Configuration

### application.yml - Production

Required environment variables:

| Variable | Description | Example |
|----------|-------------|----------|
| `SERVER_PORT` | Server port | `8081` |
| `DB_URL` | Database URL | `jdbc:postgresql://localhost:5432/users_db` |
| `DB_USER` | Database user | `postgres` |
| `DB_PASS` | Database password | `password` |
| `JWT_SECRET` | JWT secret key | `your-secret-key` |
| `JWT_EXPIRATION_MS` | Token expiration (ms) | `86400000` |
| `EUREKA_URL` | Eureka Server URL | `http://eureka:8761/eureka` |

### application-dev.yml - Development

Default values for local development:

```yaml
server.port: 8081
spring.datasource.url: jdbc:postgresql://localhost:5432/users_db
spring.datasource.username: postgres
spring.datasource.password: postgres
jwt.secret: dev-secret-key-change-in-production
eureka.client.service-url.defaultZone: http://localhost:8761/eureka
```

## 📊 Available Endpoints

### Health Check
```
GET /api/v1/health
```

### Actuator (Monitoring)
```
GET /actuator/health
GET /actuator/metrics
GET /actuator/prometheus
```

> Note: Authentication endpoints will be added in the next phase

## 🔐 Security

- **Passwords**: Encrypted with BCrypt
- **Tokens JWT**: Generated and validated with JJWT
- **CORS**: Configured as needed
- **HTTPS**: Recommended for production

## 📈 Monitoring

The service exposes Prometheus metrics at:
```
GET /actuator/prometheus
```

Available metrics:
- HTTP requests
- Response times
- Errors and exceptions
- Database connections

## 🗄️ Database

### Tables
- `users`: User information
- `roles`: Available roles
- `user_roles`: Many-to-many relationship

### Migrations
Flyway migrations are automatically applied on application startup.

Location: `src/main/resources/db/migration/`

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

## 📚 Additional Documentation

- [Spring Boot 3.3 Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Cloud Netflix Eureka](https://cloud.spring.io/spring-cloud-netflix/)
- [JJWT Documentation](https://github.com/jwtk/jjwt)

## 🤝 Contributing

Contributions are welcome. Please:

1. Fork the project
2. Create a branch for your feature (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new-feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Open a Pull Request

## 📄 License

This project is under the [MIT](LICENSE) license.

## 👨‍💼 Support

For support, contact us through the repository issues or email the development team.

---

**Last updated**: January 2026