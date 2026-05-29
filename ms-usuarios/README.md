# MS-Usuarios - Microservicio de Autenticación y Gestión de Usuarios

Microservicio responsable de la autenticación, gestión de roles y perfiles de usuario para la plataforma **WorldCupTicket**.

## 📋 Características

- **Autenticación de Usuarios**: Sistema seguro de login con JWT
- **Gestión de Roles**: Control de roles y permisos
- **Perfil de Usuario**: Gestión de información de usuario
- **Integración Eureka**: Registro automático en el Service Discovery
- **Monitoreo Prometheus**: Métricas y actuadores expuestos
- **Flyway**: Control de versiones de base de datos
- **Spring Security**: Seguridad integral de la aplicación

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.3.0**
- **Spring Cloud 2023.0.0**
- **PostgreSQL 16+**
- **Maven 3.9+**
- **JWT (JJWT 0.12.5)**
- **Lombok**

## 📦 Estructura del Proyecto

```
ms-usuarios/
├── src/
│   ├── main/
│   │   ├── java/com/worldcupticket/msusuarios/
│   │   │   ├── config/              # Configuraciones (Security, JWT, etc.)
│   │   │   ├── controller/          # Controladores REST
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── entity/              # Entidades JPA
│   │   │   ├── exception/           # Manejadores de excepciones
│   │   │   ├── mapper/              # Mappers Entity-DTO
│   │   │   ├── repository/          # Repositorios Spring Data JPA
│   │   │   ├── security/            # Seguridad (JWT, Filtros)
│   │   │   ├── service/             # Interfaces de servicios
│   │   │   ├── service/impl/        # Implementaciones de servicios
│   │   │   └── MsUsuariosApplication.java  # Clase principal
│   │   └── resources/
│   │       ├── application.yml      # Configuración principal
│   │       ├── application-dev.yml  # Configuración desarrollo
│   │       └── db/migration/        # Scripts Flyway
│   └── test/
├── pom.xml
└── README.md
```

## 🚀 Inicio Rápido

### Requisitos Previos

- Java 21 instalado
- Maven 3.9 o superior
- PostgreSQL 16 o superior
- Eureka Server corriendo en `http://localhost:8761`

### Configuración Local

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd ms-usuarios
   ```

2. **Crear base de datos PostgreSQL**
   ```sql
   CREATE DATABASE usuarios_db;
   CREATE USER postgres WITH PASSWORD 'postgres';
   ALTER ROLE postgres WITH CREATEDB;
   ```

3. **Configurar variables de entorno** (opcional)
   ```bash
   export SERVER_PORT=8081
   export DB_URL=jdbc:postgresql://localhost:5432/usuarios_db
   export DB_USER=postgres
   export DB_PASS=postgres
   export JWT_SECRET=your-secret-key-here
   export EUREKA_URL=http://localhost:8761/eureka
   ```

4. **Compilar y ejecutar**
   ```bash
   mvn clean install
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

5. **Verificar disponibilidad**
   ```bash
   curl http://localhost:8081/api/v1/health
   ```

## 📝 Configuración

### application.yml - Producción

Variables de entorno requeridas:

| Variable | Descripción | Ejemplo |
|----------|-------------|----------|
| `SERVER_PORT` | Puerto del servidor | `8081` |
| `DB_URL` | URL de la base de datos | `jdbc:postgresql://localhost:5432/usuarios_db` |
| `DB_USER` | Usuario de base de datos | `postgres` |
| `DB_PASS` | Contraseña de base de datos | `password` |
| `JWT_SECRET` | Clave secreta JWT | `your-secret-key` |
| `JWT_EXPIRATION_MS` | Expiración del token (ms) | `86400000` |
| `EUREKA_URL` | URL del Eureka Server | `http://eureka:8761/eureka` |

### application-dev.yml - Desarrollo

Valores por defecto para desarrollo local:

```yaml
server.port: 8081
spring.datasource.url: jdbc:postgresql://localhost:5432/usuarios_db
spring.datasource.username: postgres
spring.datasource.password: postgres
jwt.secret: dev-secret-key-change-in-production
eureka.client.service-url.defaultZone: http://localhost:8761/eureka
```

## 📊 Endpoints Disponibles

### Health Check
```
GET /api/v1/health
```

### Actuator (Monitoreo)
```
GET /actuator/health
GET /actuator/metrics
GET /actuator/prometheus
```

> Nota: Los endpoints de autenticación serán agregados en la siguiente fase

## 🔐 Seguridad

- **Contraseñas**: Encriptadas con BCrypt
- **Tokens JWT**: Generados y validados con JJWT
- **CORS**: Configurado según necesidades
- **HTTPS**: Recomendado en producción

## 📈 Monitoreo

El servicio expone métricas Prometheus en:
```
GET /actuator/prometheus
```

Métricas disponibles:
- Solicitudes HTTP
- Tiempos de respuesta
- Errores y excepciones
- Conexiones a base de datos

## 🗄️ Base de Datos

### Tablas
- `usuarios`: Información de usuarios
- `roles`: Roles disponibles
- `usuario_roles`: Relación muchos a muchos

### Migraciones
Las migraciones Flyway se aplican automáticamente en el inicio de la aplicación.

Ubicación: `src/main/resources/db/migration/`

## 🧪 Testing

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con coverage
mvn test jacoco:report
```

## 📚 Documentación Adicional

- [Spring Boot 3.3 Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Cloud Netflix Eureka](https://cloud.spring.io/spring-cloud-netflix/)
- [JJWT Documentation](https://github.com/jwtk/jjwt)

## 🤝 Contribución

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-feature`)
3. Commit tus cambios (`git commit -am 'Add nueva-feature'`)
4. Push a la rama (`git push origin feature/nueva-feature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la licencia [MIT](LICENSE).

## 👨‍💼 Soporte

Para soporte, contáctanos a través de los issues del repositorio o envía un email al equipo de desarrollo.

---

**Última actualización**: Enero 2026