# Laboratorio V - Creación de APIs REST con Spring Framework

Proyecto académico desarrollado con **Spring Boot**, **Java 17** y **Maven** para practicar la creación de APIs REST, el intercambio de datos en JSON y las operaciones CRUD utilizando listas en memoria.

## Información del laboratorio

- **Laboratorio:** V - Creación de APIs REST con Spring Framework
- **Sección:** C
- **Tecnologías:** Java 17, Spring Boot, Spring Web, Maven
- **Persistencia:** listas en memoria (sin base de datos)
- **Puerto:** 8080

## APIs incluidas

| # | API | Ruta base |
|---|---|---|
| 1 | Productos | `/api/productos` |
| 2 | Estudiantes | `/api/estudiantes` |
| 3 | Libros | `/api/libros` |
| 4 | Empleados | `/api/empleados` |
| 5 | Películas | `/api/peliculas` |
| 6 | Cursos | `/api/cursos` |
| 7 | Vehículos | `/api/vehiculos` |
| 8 | Tareas | `/api/tareas` |
| 9 | Clientes | `/api/clientes` |
| 10 | Pedidos | `/api/pedidos` |

Cada API contiene **al menos 5 registros iniciales** y dispone de los siguientes endpoints:

- `GET /api/recurso` - obtener todos los registros.
- `GET /api/recurso/{id}` - obtener un registro por ID.
- `POST /api/recurso` - crear un nuevo registro.
- `PUT /api/recurso/{id}` - actualizar completamente un registro.
- `PATCH /api/recurso/{id}` - actualizar únicamente los campos enviados.
- `DELETE /api/recurso/{id}` - eliminar un registro.

## Cómo ejecutar el proyecto

### Requisitos

- JDK 17 o superior.
- Maven 3.8 o superior.

### Ejecución

```bash
mvn spring-boot:run
```

La aplicación quedará disponible en:

```text
http://localhost:8080
```

Ejemplo:

```text
GET http://localhost:8080/api/productos
```

## Colección de Postman

La colección se encuentra en:

```text
postman/APIs_REST_LaboratorioV.postman_collection.json
```

Está organizada en 10 carpetas, una por API, y cada carpeta contiene:

1. GET todos
2. GET por ID
3. POST
4. PUT
5. PATCH
6. DELETE

La colección utiliza la variable:

```text
baseUrl = http://localhost:8080
```

## Ejemplos de PATCH

### Tareas

```json
{
  "completada": true
}
```

### Pedidos

```json
{
  "estado": "ENVIADO"
}
```

## Estructura principal

```text
spring-apis-lab/
├── pom.xml
├── README.md
├── .gitignore
├── postman/
│   └── APIs_REST_LaboratorioV.postman_collection.json
└── src/main/java/com/lab/apis/
    ├── ApisApplication.java
    ├── controller/
    │   ├── ProductoController.java
    │   ├── EstudianteController.java
    │   ├── LibroController.java
    │   ├── EmpleadoController.java
    │   ├── PeliculaController.java
    │   ├── CursoController.java
    │   ├── VehiculoController.java
    │   ├── TareaController.java
    │   ├── ClienteController.java
    │   └── PedidoController.java
    └── model/
        ├── Producto.java
        ├── Estudiante.java
        ├── Libro.java
        ├── Empleado.java
        ├── Pelicula.java
        ├── Curso.java
        ├── Vehiculo.java
        ├── Tarea.java
        ├── Cliente.java
        └── Pedido.java
```

## Notas

Los datos se mantienen únicamente en memoria. Al reiniciar la aplicación, se restauran los cinco registros iniciales definidos en cada controlador.
