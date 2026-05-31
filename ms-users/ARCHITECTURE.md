# MS-Users Microservice Architecture

## 📐 Overview

MS-Users is a microservice within the WorldCupTicket platform, specialized in user authentication, role management, and user profiles. It follows a hexagonal layered architecture with clear responsibilities.

## 🏗️ Layered Architecture

```
┌─────────────────────────────────────────────────────────────┐
│       REST Controller Layer                                 │
│    (HTTP Endpoints - Input/Output)                          │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│    Security & Filters Layer                                 │
│   (JWT, Authentication, Authorization)                      │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│      Service Layer (Business Logic)                         │
│   (Business logic and orchestration)                        │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│     Repository Layer (Data Access)                          │
│   (JPA, Spring Data - Data access)                          │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│   PostgreSQL Database (Persistence)                         │
│  (Persistent data storage)                                  │
└─────────────────────────────────────────────────────────────┘
```

## 📦 Main Components

### 1. **Controller Layer** (`controller/`)
- **HealthController**: Service health verification
- Future controllers for authentication, users, and roles

**Responsibilities:**
- Receive HTTP requests
- Validate basic input
- Delegate to service
- Return formatted responses

### 2. **Security Layer** (`security/`)

#### JwtTokenProvider
- JWT token generation
- Token validation
- Claims extraction

#### JwtAuthenticationFilter
- Filter for each HTTP request
- Token validation in headers
- Security context establishment

### 3. **Service Layer** (`service/` & `service/impl/`)

#### AuthService
- User authentication
- Token response generation
- Credential validation

#### UserService
- CRUD operations for users
- Search by email/ID
- Profile information

#### RoleService
- Role management
- Search by ID/name
- CRUD operations

### 4. **Repository Layer** (`repository/`)

Spring Data JPA interfaces:
- **UserRepository**: User data access
- **RoleRepository**: Role data access
- **UserRoleRepository**: User-Role relationship

### 5. **Entity Layer** (`entity/`)

JPA entities mapped to database:
- **User**: User information
- **Role**: Role definition
- **UserRole**: Many-to-many relationship

### 6. **DTO Layer** (`dto/`)

Data transfer objects:
- **UserDTO**: Public user data
- **LoginRequest**: User credentials
- **AuthResponse**: Response with token and user

### 7. **Mapper Layer** (`mapper/`)
- **UserMapper**: Entity ↔ DTO conversion
- Maintains separation between layers

### 8. **Configuration Layer** (`config/`)

#### SecurityConfig
- Spring Security configuration
- Filter definition
- Authentication management

#### JwtConfig
- JWT properties from `application.yml`
- Configuration injection

### 9. **Exception Handler** (`exception/`)
- **GlobalExceptionHandler**: Centralized exception handling
- Consistent error responses
- Safe error traces

## 🔄 Authentication Flow

```
1. Client sends POST /api/v1/auth/login
   ├─ LoginRequest (email, password)
   │
2. AuthController validates and delegates to AuthService
   │
3. AuthService authenticates credentials
   ├─ Search user in UserRepository
   ├─ Validate password with BCrypt
   │
4. If valid, generate JWT token
   ├─ JwtTokenProvider.generateToken()
   ├─ Include email, roles, claims
   │
5. Return AuthResponse
   ├─ JWT Token
   ├─ Token type (Bearer)
   ├─ Expiration
   ├─ UserDTO with public data
   │
6. Client stores token and includes in Authorization header
   │
7. JwtAuthenticationFilter intercepts request
   ├─ Extract token from header
   ├─ Validate with JwtTokenProvider
   ├─ Establish SecurityContext
   │
8. Request processes with authentication established
```

## 🗄️ Data Model

### Table: `users`
```sql
┌─────────────────────┬──────────────┬─────────────┐
│ Field               │ Type         │ Constraint  │
├─────────────────────┼──────────────┼─────────────┤
│ id                  │ BIGSERIAL    │ PK          │
│ email               │ VARCHAR(255) │ UK, NOT NULL│
│ password            │ VARCHAR(255) │ NOT NULL    │
│ first_name          │ VARCHAR(100) │ NOT NULL    │
│ last_name           │ VARCHAR(100) │ NOT NULL    │
│ active              │ BOOLEAN      │ NOT NULL    │
│ created_at          │ TIMESTAMP    │ NOT NULL    │
│ updated_at          │ TIMESTAMP    │ NOT NULL    │
└─────────────────────┴──────────────┴─────────────┘
```

### Table: `roles`
```sql
┌─────────────────────┬──────────────┬─────────────┐
│ Field               │ Type         │ Constraint  │
├─────────────────────┼──────────────┼─────────────┤
│ id                  │ BIGSERIAL    │ PK          │
│ name                │ VARCHAR(50)  │ UK, NOT NULL│
│ description         │ VARCHAR(255) │             │
│ active              │ BOOLEAN      │ NOT NULL    │
│ created_at          │ TIMESTAMP    │ NOT NULL    │
│ updated_at          │ TIMESTAMP    │ NOT NULL    │
└─────────────────────┴──────────────┴─────────────┘
```

### Table: `user_roles`
```sql
┌──────────────────┬──────────┬─────────────────┐
│ Field            │ Type     │ Constraint      │
├──────────────────┼──────────┼─────────────────┤
│ id               │ BIGSERIAL│ PK              │
│ user_id          │ BIGINT   │ FK, NOT NULL    │
│ role_id          │ BIGINT   │ FK, NOT NULL    │
│ assigned_at      │ TIMESTAMP│ NOT NULL        │
└──────────────────┴──────────┴─────────────────┘
```

## 🔌 Integration with Other Microservices

### Discovery (Eureka)
- MS-Users registers automatically
- Location: `http://eureka:8761/eureka`
- Available as `ms-users`

### Inter-service Communication
- Future: Use RestTemplate or Feign Client
- Synchronous calls to other services
- Circuit breaker via Hystrix/Resilience4j

## 📊 Monitoring and Observability

### Metrics (Prometheus)
```
GET /actuator/prometheus
```
- HTTP metrics
- JVM metrics
- DB connections
- Custom metrics

### Health Check
```
GET /actuator/health
```
- Overall service status
- Database status
- Eureka status

### Logging
- DEBUG level for development
- INFO level for production
- Structured logging with SLF4J

## 🔐 Security Considerations

### Authentication
- Passwords encrypted with BCrypt
- Secure password comparison

### Authorization
- Centralized roles and permissions
- Endpoint validation

### JWT Tokens
- Minimum 32 character secret key
- Configurable expiration
- Refresh tokens (future)

### HTTPS
- Recommended for production
- Configuration in server.ssl

## 🚀 Scalability

- **Database**: Connection pooling (HikariCP)
- **Cache**: Future Redis for tokens/frequent users
- **Load Balancing**: Multiple instances behind balancer
- **Circuit Breaker**: Failure protection

## 📈 Future Evolution

- [ ] Refresh tokens
- [ ] 2FA (Two-Factor Authentication)
- [ ] OAuth2/OpenID Connect
- [ ] Rate limiting
- [ ] Audit logging
- [ ] Distributed cache
- [ ] Event-driven architecture
- [ ] Async messaging (RabbitMQ/Kafka)

## 📚 Design Patterns Used

| Pattern | Location | Purpose |
|---------|----------|----------|
| Repository | repository/ | Data access abstraction |
| DTO | dto/ | Safe data transfer |
| Mapper | mapper/ | Entity-DTO conversion |
| Service | service/ | Centralized business logic |
| Factory | config/ | Bean creation |
| Strategy | security/ | Different authentication strategies |
| Chain of Responsibility | security/ | Security filters |
| Template Method | entity/ | JPA callbacks (@PrePersist) |

---

**Last updated**: January 2026