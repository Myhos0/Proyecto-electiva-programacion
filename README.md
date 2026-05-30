# Smart Network Management

Sistema desarrollado con Spring Boot para la gestión de rutas entre ciudades de Colombia utilizando estructuras de grafos, consumo de APIs externas y persistencia en SQL Server.

---

# Descripción del Proyecto

El proyecto simula una red de ciudades conectadas mediante rutas bidireccionales donde cada conexión posee un tiempo de recorrido.

La aplicación permite:

- Consumir ciudades desde la API pública de Colombia.
- Crear grafos aleatorios de ciudades.
- Construir grafos a partir de rutas almacenadas en base de datos.
- Crear nuevas rutas entre ciudades existentes.
- Persistir rutas en SQL Server.
- Consultar la estructura completa del grafo mediante endpoints REST.

---

# Tecnologías Utilizadas

- Java 21
- Spring Boot
- Spring WebFlux (`WebClient`)
- Spring Data JPA
- SQL Server
- Maven
- Lombok

---

# Arquitectura del Proyecto

```text
src/main/java/com/etitc/smart_network_managment
│
├── controller
│   └── GrafoController.java
│
├── service
│   ├── GrafoService.java
│   └── CiudadService.java
│
├── repository
│   └── RutaRepository.java
│
├── entity
│   └── RutaEntity.java
│
├── dto
│   ├── RutaDTO.java
│   ├── CiudadDTO.java
│   ├── ConexionDTO.java
│   └── CiudadGrafoDTO.java
│
├── graph
│   ├── Grafo.java
│   └── Ruta.java
│
└── SmartNetworkManagmentApplication.java
```

---

# Funcionalidades

## 1. Grafo Aleatorio

Genera automáticamente un grafo con máximo 10 ciudades aleatorias obtenidas desde la API de Colombia.

Cada ciudad queda conectada mediante rutas bidireccionales con tiempos aleatorios.

---

## 2. Grafo desde Base de Datos

Construye el grafo utilizando las rutas almacenadas en SQL Server.

Si no existen rutas almacenadas, el sistema genera automáticamente un grafo aleatorio.

---

## 3. Creación de Rutas

Permite crear nuevas rutas entre ciudades existentes.

### Validaciones implementadas

- La ciudad origen no puede ser igual al destino.
- No se permiten rutas duplicadas.
- Las ciudades deben existir en la API.
- El grafo se actualiza en memoria y en la base de datos.

---

# API Externa Utilizada

Se utiliza la API pública de Colombia:

https://api-colombia.com/api/v1/city

---

# Endpoints

## Obtener grafo actual

```http
GET /grafo
```

---

## Obtener grafo aleatorio

```http
GET /grafo/aleatorio
```

---

## Obtener grafo desde base de datos

```http
GET /grafo/bd
```

---

## Crear nueva ruta

```http
POST /grafo/ruta
```

### Body

```json
{
  "origen": "Bogotá D.C.",
  "destino": "Medellin",
  "tiempo": 4
}
```

---

# Configuración SQL Server

## application.properties

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=smart_network;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# Dependencias Maven

## SQL Server

```xml
<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## Lombok

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

---

# Ejecución del Proyecto

## 1. Clonar repositorio

```bash
git clone URL_DEL_REPOSITORIO
```

---

## 2. Instalar dependencias

```bash
mvn clean install
```

---

## 3. Ejecutar aplicación

```bash
mvn spring-boot:run
```

---

# Ejemplo de Respuesta

```json
[
  {
    "ciudad": {
      "id": 694,
      "name": "Riohacha"
    },
    "conexiones": [
      {
        "destino": {
          "id": 574,
          "name": "Mosquera"
        },
        "tiempo": 4
      }
    ]
  }
]
```

---

# Mejoras Futuras

- Implementar algoritmos de rutas mínimas (Dijkstra).
- Persistir ciudades en base de datos.
- Documentación Swagger/OpenAPI.

---

# Autor

Miguel Ospina
Luis Gil
