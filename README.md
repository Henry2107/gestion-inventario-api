# 📦 Sistema de Gestión de Inventarios y Proveedores (API REST)

Backend desarrollado en **Java** y **Spring Boot** para la administración de inventarios, control de stock y gestión de proveedores. Construido bajo arquitectura limpia en capas, con validación de reglas de negocio, pruebas unitarias automatizadas y documentación interactiva.

## 🛠️ Tecnologías Utilizadas
* **Lenguaje:** Java 27
* **Framework:** Spring Boot 4.0.5 (Spring Web, Spring Data JPA)
* **Base de Datos:** H2 Database (en memoria para desarrollo)
* **Pruebas:** JUnit 5 y Mockito
* **Documentación API:** Swagger / OpenAPI 3 (SpringDoc)
* **Gestor de Dependencias:** Maven

## 🏗️ Arquitectura del Proyecto
El código sigue el patrón estándar de separación de responsabilidades:
* `model`: Entidades JPA (`Product`, `Supplier`) con relación `@ManyToOne` y manejo financiero mediante `BigDecimal`.
* `repository`: Interfaces que extienden de `JpaRepository` con *Query Methods* personalizados (`findByNit`, `findByStockLessThan`).
* `service`: Lógica de negocio, validación de NIT único, verificación de existencia de proveedores y control de stock negativo.
* `controller`: Endpoints RESTful con respuestas HTTP semánticas (`ResponseEntity`).

## 🚀 Cómo Ejecutar el Proyecto
1. Clona este repositorio en tu máquina local.
2. Ejecuta la aplicación desde tu IDE o mediante Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Accede a la documentación interactiva de **Swagger UI** en tu navegador para probar todos los endpoints:
   `http://localhost:8080/swagger-ui/index.html`
