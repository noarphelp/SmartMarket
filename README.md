<img width="1024" height="1024" alt="smartMarket" src="https://github.com/user-attachments/assets/5f03c852-a7c9-4c5d-83dc-4971dff8fbc1" />

# SmartMarket - Proyecto Final Bootcamp 🚀​🚀​🚀​

---

## Descripción 📝​

SmartMarket es una aplicación backend en Spring Boot para la gestión de ventas de un SUPERMERCADO.  
Incluye entidades, servicios, controladores, excepciones, y pruebas unitarias e integración para asegurar la calidad.

---

## Estructura del Proyecto 🏗️​

- **src/main/java**: Código fuente (controladores, servicios, entidades, DTOs, excepciones, mappers, repositorios)
- **src/test/java**: Tests unitarios y de integración para sucursal ,producto y detalleVenta.
- **sqlDePrueba.txt**: Script SQL para crear la base de datos y cargar datos de ejemplo.
- **postman_collection.json**: Colección Postman para probar los endpoints de la API.
- **SonarQube.png**: Captura de pantalla del análisis realizado con SonarQube a la aplicación.

---

## Requisitos ✅​

- Java 17+
- Maven o Gradle
- Base de datos MySQL (configurable en application.properties)
- Postman (para pruebas)

---

## Instalación y Ejecución ⚠️​🔝​

1. Clonar el repositorio:
    ```bash
    git clone https://github.com/noarphelp/SmartMarket.git
    cd SmartMarket
    ```

2. Configurar la conexión a la base de datos en `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/smart_market
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    spring.jpa.hibernate.ddl-auto=update
    ```

3. Ejecutar el script SQL para crear la base y cargar datos de ejemplo:

    ```bash
    mysql -u tu_usuario -p smart_market < sqlDePrueba.txt
    ```

4. Ejecutar la aplicación:

    ```bash
    ./mvnw spring-boot:run
    ```

5. Importar la colección Postman (`postman_collection.json`) para probar la API.

---

## Tests 🔁​

- Tests unitarios con `@WebMvcTest` para controladores.
- Tests de integración con `@SpringBootTest` y rollback automático para servicios.
- Uso de `MockMvc` y `TestRestTemplate` según corresponda.

Para ejecutar los tests:

```bash
./mvnw test
```

---

## Contacto 📩​

Para dudas o sugerencias, pueden contactarnos a través del repositorio de GitHub.


