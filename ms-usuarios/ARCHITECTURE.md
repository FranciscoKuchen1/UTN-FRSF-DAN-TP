# Arquitectura del Microservicio MS-Usuarios

## 📐 Visión General

MS-Usuarios es un microservicio dentro de la plataforma WorldCupTicket, especializado en autenticación, gestión de roles y perfiles de usuario. Sigue una arquitectura en capas hexagonal con responsabilidades claras.

## 🏗️ Estructura en Capas

```
┌─────────────────────────────────────────┐
│       REST Controller Layer             │
│    (Endpoints HTTP - Entrada/Salida)    │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│    Security & Filters Layer             │
│   (JWT, Authentication, Authorization) │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│      Service Layer (Business Logic)     │
│   (Lógica de negocio y orquestación)   │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│     Repository Layer (Data Access)      │
│   (JPA, Spring Data - Acceso a datos)  │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│   PostgreSQL Database (Persistencia)    │
│  (Almacenamiento de datos persistente) │
└─────────────────────────────────────────┘
```

## 📦 Componentes Principales

### 1. **Controller Layer** (`controller/`)
- **HealthController**: Verificación de salud del servicio
- Futuros controladores para autenticación, usuarios y roles

**Responsabilidades:**
- Recibir solicitudes HTTP
- Validar entrada básica
- Delegarlas al servicio
- Retornar respuestas formateadas

### 2. **Security Layer** (`security/`)

#### JwtTokenProvider
- Generación de tokens JWT
- Validación de tokens
- Extracción de claims

#### JwtAuthenticationFilter
- Filtro para cada solicitud HTTP
- Validación del token en headers
- Establecimiento del contexto de seguridad

### 3. **Service Layer** (`service/` & `service/impl/`)

#### AuthService
- Autenticación de usuarios
- Generación de respuestas con tokens
- Validación de credenciales

#### UsuarioService
- CRUD de usuarios
- Búsqueda por email/ID
- Información de perfil

#### RolService
- Gestión de roles
- Búsqueda por ID/nombre
- Operaciones CRUD

### 4. **Repository Layer** (`repository/`)

Interfaces Spring Data JPA:
- **UsuarioRepository**: Acceso a datos de Usuario
- **RolRepository**: Acceso a datos de Rol
- **UsuarioRolRepository**: Relación Usuario-Rol

### 5. **Entity Layer** (`entity/`)

Entidades JPA mapeadas a base de datos:
- **Usuario**: Información de usuario
- **Rol**: Definición de roles
- **UsuarioRol**: Relación muchos-a-muchos

### 6. **DTO Layer** (`dto/`)

Objetos de transferencia de datos:
- **UsuarioDTO**: Datos públicos del usuario
- **LoginRequestDTO**: Credenciales de login
- **AuthResponseDTO**: Respuesta con token y usuario

### 7. **Mapper Layer** (`mapper/`)
- **UsuarioMapper**: Conversión Entity ↔ DTO
- Mantiene separación entre capas

### 8. **Configuration Layer** (`config/`)

#### SecurityConfig
- Configuración de Spring Security
- Definición de filtros
- Gestión de autenticación

#### JwtConfig
- Propiedades de JWT desde `application.yml`
- Inyección de configuración

### 9. **Exception Handler** (`exception/`)
- **GlobalExceptionHandler**: Manejo centralizado de excepciones
- Respuestas de error consistentes
- Trazas de errores seguras

## 🔄 Flujo de Autenticación

```
1. Cliente envía POST /api/v1/auth/login
   ├─ LoginRequestDTO (email, password)
   │
2. AuthController valida y delega a AuthService
   │
3. AuthService autentica credenciales
   ├─ Busca usuario en UsuarioRepository
   ├─ Valida contraseña con BCrypt
   │
4. Si es válido, genera token JWT
   ├─ JwtTokenProvider.generarToken()
   ├─ Incluye email, roles, claims
   │
5. Retorna AuthResponseDTO
   ├─ Token JWT
   ├─ Tipo de token (Bearer)
   ├─ Expiración
   ├─ UsuarioDTO con datos públicos
   │
6. Cliente almacena token y lo incluye en Authorization header
   │
7. JwtAuthenticationFilter intercepta solicitud
   ├─ Extrae token del header
   ├─ Valida con JwtTokenProvider
   ├─ Establece SecurityContext
   │
8. Solicitud procesa con autenticación establecida
```

## 🗄️ Modelo de Datos

### Tabla: `usuarios`
```sql
┌─────────────────────┬──────────────┬─────────────┐
│ Campo               │ Tipo         │ Restricción │
├─────────────────────┼──────────────┼─────────────┤
│ id                  │ BIGSERIAL    │ PK          │
│ email               │ VARCHAR(255) │ UK, NOT NULL│
│ password            │ VARCHAR(255) │ NOT NULL    │
│ nombre              │ VARCHAR(100) │ NOT NULL    │
│ apellido            │ VARCHAR(100) │ NOT NULL    │
│ activo              │ BOOLEAN      │ NOT NULL    │
│ fecha_creacion      │ TIMESTAMP    │ NOT NULL    │
│ fecha_actualizacion │ TIMESTAMP    │ NOT NULL    │
└─────────────────────┴──────────────┴─────────────┘
```

### Tabla: `roles`
```sql
┌─────────────────────┬──────────────┬─────────────┐
│ Campo               │ Tipo         │ Restricción │
├─────────────────────┼──────────────┼─────────────┤
│ id                  │ BIGSERIAL    │ PK          │
│ nombre              │ VARCHAR(50)  │ UK, NOT NULL│
│ descripcion         │ VARCHAR(255) │             │
│ activo              │ BOOLEAN      │ NOT NULL    │
│ fecha_creacion      │ TIMESTAMP    │ NOT NULL    │
│ fecha_actualizacion │ TIMESTAMP    │ NOT NULL    │
└─────────────────────┴──────────────┴─────────────┘
```

### Tabla: `usuario_roles`
```sql
┌──────────────────┬──────────┬─────────────────┐
│ Campo            │ Tipo     │ Restricción     │
├──────────────────┼──────────┼─────────────────┤
│ id               │ BIGSERIAL│ PK              │
│ usuario_id       │ BIGINT   │ FK, NOT NULL    │
│ rol_id           │ BIGINT   │ FK, NOT NULL    │
│ fecha_asignacion │ TIMESTAMP│ NOT NULL        │
└──────────────────┴──────────┴─────────────────┘
```

## 🔌 Integración con Otros Microservicios

### Discovery (Eureka)
- MS-Usuarios se registra automáticamente
- Ubicación: `http://eureka:8761/eureka`
- Disponible como `ms-usuarios`

### Comunicación Interservicios
- Futuro: Usar RestTemplate o Feign Client
- Llamadas síncronas a otros servicios
- Circuit breaker mediante Hystrix/Resilience4j

## 📊 Monitoreo y Observabilidad

### Metrics (Prometheus)
```
GET /actuator/prometheus
```
- Métricas de HTTP
- Métricas JVM
- Conexiones DB
- Métricas personalizadas

### Health Check
```
GET /actuator/health
```
- Estado general del servicio
- Estado de base de datos
- Estado de Eureka

### Logging
- Nivel DEBUG para desarrollo
- Nivel INFO para producción
- Logs estructurados con SLF4J

## 🔐 Consideraciones de Seguridad

### Autenticación
- Contraseñas encriptadas con BCrypt
- Comparación segura de contraseñas

### Autorización
- Roles y permisos centralizados
- Validación en endpoints

### Tokens JWT
- Clave secreta de mínimo 32 caracteres
- Expiración configurable
- Refresh tokens (futuro)

### HTTPS
- Recomendado en producción
- Configuración en server.ssl

## 🚀 Escalabilidad

- **Base de datos**: Pool de conexiones (HikariCP)
- **Cache**: Futuro Redis para tokens/usuarios frecuentes
- **Load Balancing**: Múltiples instancias detrás de balanceador
- **Circuit Breaker**: Protección ante fallos

## 📈 Evolución Futura

- [ ] Refresh tokens
- [ ] 2FA (Two-Factor Authentication)
- [ ] OAuth2/OpenID Connect
- [ ] Rate limiting
- [ ] Audit logging
- [ ] Cache distribuido
- [ ] Event-driven architecture
- [ ] Mensajería asíncrona (RabbitMQ/Kafka)

## 📚 Patrones de Diseño Utilizados

| Patrón | Ubicación | Propósito |
|--------|-----------|----------|
| Repository | repository/ | Abstracción de acceso a datos |
| DTO | dto/ | Transferencia segura de datos |
| Mapper | mapper/ | Conversión Entity-DTO |
| Service | service/ | Lógica de negocio centralizada |
| Factory | config/ | Creación de beans |
| Strategy | security/ | Diferentes estrategias de autenticación |
| Chain of Responsibility | security/ | Filtros de seguridad |
| Template Method | entity/ | Callbacks JPA (@PrePersist) |

---

**Fecha de actualización**: Enero 2026