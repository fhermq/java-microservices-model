# Pixan Parent Project 🚀

**Pixan** es una arquitectura de microservicios escalable basada en Spring Boot 3.5.7 con Java 21. El proyecto implementa un dominio de negocio completo con tres microservicios independientes: Clientes, Productos y Facturación.

## 📋 Tabla de Contenidos

- [Características](#características)
- [Requisitos Previos](#requisitos-previos)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Instalación y Configuración](#instalación-y-configuración)
- [Ejecución](#ejecución)
- [Microservicios](#microservicios)
- [API Documentation](#api-documentation)
- [Tecnologías](#tecnologías)
- [Configuración](#configuración)
- [Testing](#testing)
- [Contribute](#contribute)

## ✨ Características

- ✅ Arquitectura de microservicios con Spring Boot 3
- ✅ Base de datos con JPA/Hibernate
- ✅ Documentación API automática con SpringDoc OpenAPI (Swagger)
- ✅ Spring Cloud para comunicación entre servicios
- ✅ Proyectos independientes pero coordinados
- ✅ Mapeo de objetos con MapStruct
- ✅ Inyección de dependencias automática
- ✅ Hot reload con Spring Boot DevTools
- ✅ Testing completo con JUnit 5 y Mockito
- ✅ Seguridad versionada (sin credenciales expuestas)

## 📋 Requisitos Previos

Asegúrate de tener instalado:

- **Java 21 o superior**
  ```bash
  java -version
  ```
- **Maven 3.9.x o superior**
  ```bash
  mvn -version
  ```
- **Git** (para clonar el repositorio)

### Instalación de Dependencias (macOS)

Si usas Homebrew:

```bash
# Instalar Java 21
brew install openjdk@21

# Instalar Maven
brew install maven

# Verificar instalación
java --version
mvn --version
```

## 📁 Estructura del Proyecto

```
pixan-parent-project/
├── README.md                          # Este archivo
├── pom.xml                            # POM padre (configuración global)
├── businessdomain/                    # Dominio de negocio
│   ├── pom.xml                        # POM del módulo de negocio
│   ├── customers/                     # Microservicio de Clientes
│   │   ├── pom.xml
│   │   ├── src/main/java/com/pixan/customer/
│   │   │   ├── CustomersApplication.java
│   │   │   ├── controller/            # REST Controllers
│   │   │   ├── entities/              # Entidades JPA
│   │   │   └── repository/            # Repositorios Spring Data
│   │   └── src/main/resources/
│   │       └── application.properties
│   ├── billing/                       # Microservicio de Facturación
│   │   ├── pom.xml
│   │   ├── src/main/java/com/pixan/billing/
│   │   │   ├── InvoiceApplication.java
│   │   │   ├── controller/
│   │   │   ├── entities/
│   │   │   └── repository/
│   │   └── src/main/resources/
│   │       └── application.properties
│   └── product/                       # Microservicio de Productos
│       ├── pom.xml
│       ├── src/main/java/com/pixan/product/
│       │   ├── ProductApplication.java
│       │   ├── controller/
│       │   ├── entities/
│       │   └── repository/
│       └── src/main/resources/
│           └── application.properties
├── infraestructuredomain/             # Dominio de infraestructura
│   └── pom.xml
└── .gitignore                         # Configuración de Git
```

## 🔧 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/pixan-parent-project.git
cd pixan-parent-project
```

### 2. Compilar el Proyecto

Desde la raíz del proyecto:

```bash
# Compilar todo el proyecto (parent + todos los módulos)
mvn clean install

# Compilar sin ejecutar tests
mvn clean install -DskipTests

# Compilar un módulo específico
mvn clean install -pl businessdomain/customers
```

### 3. Configuración de Bases de Datos

Cada microservicio usa **H2 Database** por defecto (en memoria), ideal para desarrollo. Las propiedades se encuentran en:

- `businessdomain/customers/src/main/resources/application.properties`
- `businessdomain/billing/src/main/resources/application.properties`
- `businessdomain/product/src/main/resources/application.properties`

Para usar PostgreSQL, actualiza:

```properties
# Cambiar de H2 a PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/pixan_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL10Dialect
```

## ▶️ Ejecución

### Opción 1: Ejecutar desde la IDE (recomendado)

1. Abre el proyecto en IntelliJ IDEA o Eclipse
2. Navega a la clase principal de cada microservicio:
   - `com.pixan.customer.CustomersApplication`
   - `com.pixan.billing.InvoiceApplication`
   - `com.pixan.product.ProductApplication`
3. Click derecho → Run

### Opción 2: Ejecutar desde la Terminal

```bash
# Microservicio de Clientes (puerto 8080)
mvn spring-boot:run -pl businessdomain/customers

# Microservicio de Facturación (puerto 8081)
mvn spring-boot:run -pl businessdomain/billing

# Microservicio de Productos (puerto 8083)
mvn spring-boot:run -pl businessdomain/product
```

### Opción 3: Ejecutar JAR compilado

```bash
# Compilar
mvn clean package -pl businessdomain/customers -DskipTests

# Ejecutar
java -jar businessdomain/customers/target/customers-0.0.1-SNAPSHOT.jar
```

## 🎯 Microservicios

### 1. **Customers Service** (Puerto 8080)
Gestión de clientes del sistema.

- **Entidades**: Customer
- **Endpoints**:
  - `GET /customers` - Listar todos los clientes
  - `GET /customers/{id}` - Obtener cliente por ID
  - `POST /customers` - Crear nuevo cliente
  - `PUT /customers/{id}` - Actualizar cliente
  - `DELETE /customers/{id}` - Eliminar cliente

### 2. **Billing Service** (Puerto 8081)
Gestión de facturas e invoices.

- **Entidades**: Invoice
- **Endpoints**:
  - `GET /invoices` - Listar todas las facturas
  - `GET /invoices/{id}` - Obtener factura por ID
  - `POST /invoices` - Crear nueva factura
  - `PUT /invoices/{id}` - Actualizar factura
  - `DELETE /invoices/{id}` - Eliminar factura

### 3. **Product Service** (Puerto 8083)
Gestión del catálogo de productos.

- **Entidades**: Product
- **Endpoints**:
  - `GET /products` - Listar todos los productos
  - `GET /products/{id}` - Obtener producto por ID
  - `POST /products` - Crear nuevo producto
  - `PUT /products/{id}` - Actualizar producto
  - `DELETE /products/{id}` - Eliminar producto

## 📚 API Documentation

La documentación interactiva de las APIs está disponible mediante **Swagger UI**:

- **Customers**: http://localhost:8080/swagger-ui.html
- **Billing**: http://localhost:8081/swagger-ui.html
- **Products**: http://localhost:8083/swagger-ui.html

También puedes acceder a los specs en formato JSON:

- **Customers**: http://localhost:8080/v3/api-docs
- **Billing**: http://localhost:8081/v3/api-docs
- **Products**: http://localhost:8083/v3/api-docs

## 🛠️ Tecnologías

### Spring Boot Ecosystem
- **Spring Boot 3.5.7** - Framework base
- **Spring Data JPA** - Persistencia de datos
- **Spring Web** - REST APIs
- **Spring WebFlux** - Programación reactiva
- **Spring Cloud** - Comunicación entre servicios

### Librerías Adicionales
- **Lombok** (v1.18.42) - Reduce boilerplate
- **MapStruct** (v1.5.5) - Mapeo de objetos
- **SpringDoc OpenAPI** (v2.3.0) - Documentación de APIs
- **H2 Database** - Base de datos en memoria para desarrollo
- **PostgreSQL** (opcional) - Base de datos relacional
- **Mockito** (v5.20.0) - Testing

### Stack de Compilación
- **Java 21** - Lenguaje de programación
- **Maven 3.9.x** - Gestión de dependencias
- **JUnit 5** - Framework de testing

## ⚙️ Configuración

### Variables de Entorno

Se recomienda usar variables de entorno para configuración sensible:

```bash
# Crear archivo .env (no versionado)
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
export API_KEY=your_api_key
```

### Perfiles de Spring (Environments)

Crear archivos específicos por ambiente:

```
application.properties          # Configuración por defecto
application-local.properties    # Configuración local (no versionada)
application-dev.properties      # Configuración de desarrollo
application-prod.properties     # Configuración de producción
```

Ejecutar con un perfil específico:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

## 🧪 Testing

### Ejecutar todos los tests

```bash
# Todos los tests del proyecto
mvn test

# Tests de un módulo específico
mvn test -pl businessdomain/customers

# Ejecutar una clase de test específica
mvn test -Dtest=CustomerRepositoryTest
```

### Ejecutar con cobertura

```bash
mvn clean test jacoco:report
```

La cobertura se genera en: `target/site/jacoco/index.html`

## 🔒 Seguridad

✅ **Análisis de Seguridad Completado**

- No hay credenciales versionadas en el repositorio
- `.gitignore` configurado correctamente para archivos sensibles
- Se excluyen: `.env`, `.env.*`, `application-local.properties`
- Variables de entorno para configuración sensible

## 📝 Guías Útiles

### Agregar una Nueva Dependencia

1. Edita el `pom.xml` del módulo o del padre
2. Añade la dependencia
3. Ejecuta `mvn clean install`

Ejemplo:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### Crear un Nuevo REST Controller

```java
@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor
public class ResourceController {
    
    private final ResourceService service;
    
    @GetMapping
    public ResponseEntity<List<ResourceDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
```

### Crear un Nuevo Repositorio

```java
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByName(String name);
}
```

## 🐛 Troubleshooting

### Error: "Java version is wrong"
Verifica que tienes Java 21+:
```bash
java --version
```

### Error: "Cannot build module, missing dependency"
Limpia y reinstala el proyecto:
```bash
mvn clean install
```

### Puerto ya está en uso
Cambia el puerto en `application.properties`:
```properties
server.port=8085
```

### H2 Console no funciona
Habilítalo en `application.properties`:
```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Accede a: http://localhost:8080/h2-console

## 📖 Documentación Adicional

- [Spring Boot Official Docs](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [Maven Documentation](https://maven.apache.org/guides/)

## 🤝 Contribute

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo licencia MIT. Ver `LICENSE` para más detalles.

## 👨‍💻 Autor

**Pixan Development Team**

## 📞 Soporte

Para reportar issues o sugerencias, abre un issue en el repositorio.

---

**Última actualización**: 15 de enero de 2026

¡Gracias por usar Pixan! 🎉
