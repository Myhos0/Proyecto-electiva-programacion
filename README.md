# Smart Network Management

Sistema desarrollado con Spring Boot para la gestión de rutas entre ciudades de Colombia utilizando estructuras de grafos, persistencia en SQL Server y algoritmos de búsqueda de caminos mínimos.

---

## Descripción

Smart Network Management permite modelar una red de ciudades conectadas mediante rutas bidireccionales con tiempos de recorrido asociados.

El sistema consume información de ciudades desde la API pública de Colombia y permite construir grafos tanto de forma aleatoria como a partir de información almacenada en una base de datos SQL Server.

Además, implementa el algoritmo de Dijkstra para calcular la ruta más corta entre dos ciudades.

---

## Tecnologías Utilizadas

* Java 21
* Spring Boot
* Spring WebFlux (WebClient)
* Spring Data JPA
* SQL Server
* Maven
* Lombok

---

## Arquitectura

```text
src/main/java/com/etitc/smart_network_managment
│
├── controller
│   └── GrafoController
│
├── service
│   ├── GrafoService
│   └── CiudadService
│   └── CiuadadAPIService
|
├── repository
│   ├── RutaRepository
│   └── CiudadRepository
│
├── entity
│   ├── RutaEntity
│   └── CiudadEntity
│
├── dto
│   ├── CiudadDTO
│   ├── RutaDTO
│   ├── ConexionDTO
│   ├── CiudadGrafoDTO
│   └── RutaMinimaDTO
│
├── graph
│   ├── Grafo
│   ├── Ruta
│   └── RutaDijkstra
|
├── model
|  └── Ruta  
│
|
├── webclient
|  └── WebClientConfig
|
└── SmartNetworkManagmentApplication
```

---

## Funcionalidades

### Gestión de Ciudades

* Obtención de ciudades desde la API Colombia.
* Búsqueda de ciudades por nombre.
* Persistencia local de ciudades utilizadas en rutas.

---

### Gestión de Rutas

* Creación de rutas entre ciudades existentes.
* Validación de ciudades origen y destino.
* Prevención de rutas duplicadas.
* Almacenamiento de rutas en SQL Server.
* Actualización automática del grafo en memoria.

---

### Grafo Aleatorio

Genera un grafo con un máximo de 10 ciudades seleccionadas aleatoriamente desde la API.

Características:

* Selección aleatoria de ciudades.
* Conexiones bidireccionales.
* Tiempos de recorrido aleatorios.
* Conversión automática a DTO para exponer mediante API REST.

---

### Grafo desde Base de Datos

Construye un grafo utilizando las rutas almacenadas en SQL Server.

Características:

* Recuperación de ciudades persistidas.
* Reconstrucción de conexiones.
* Actualización dinámica al crear nuevas rutas.

---

### Algoritmo de Dijkstra

Permite calcular la ruta mínima entre dos ciudades.

El algoritmo:

* Calcula el menor tiempo total de recorrido.
* Reconstruye el camino completo.
* Funciona tanto para grafos aleatorios como para grafos persistidos.

Ejemplo:

```text
Bogotá -> Medellín = 5 horas
Medellín -> Cali = 2 horas
Bogotá -> Cali = 8 horas
```

Resultado:

```json
{
  "origen": "Bogotá D.C.",
  "destino": "Cali",
  "distanciaTotal": 7,
  "ruta": [
    "Bogotá D.C.",
    "Medellín",
    "Cali"
  ]
}
```

---

## Base de Datos

### Tabla ciudades

```sql
CREATE TABLE ciudades(
    id INT PRIMARY KEY,
    nombre VARCHAR(255)
);
```

### Tabla rutas

```sql
CREATE TABLE rutas(
    id BIGINT IDENTITY PRIMARY KEY,
    origen_id INT NOT NULL,
    destino_id INT NOT NULL,
    tiempo INT NOT NULL,

    FOREIGN KEY (origen_id)
        REFERENCES ciudades(id),

    FOREIGN KEY (destino_id)
        REFERENCES ciudades(id)
);
```

---

## Endpoints

### Obtener grafo actual

```http
GET /grafo
```

---

### Obtener grafo aleatorio

```http
GET /grafo/aleatorio
```

---

### Obtener grafo desde base de datos

```http
GET /grafo/bd
```

---

### Crear nueva ruta

```http
POST /grafo/ruta
```

Body:

```json
{
  "origen": "Bogota",
  "destino": "Medellin",
  "tiempo": 4
}
```

---

### Calcular ruta mínima (Dijkstra)

```http
GET /grafo/ruta-minima?origen=Bogota&destino=Cali
```

Respuesta:

```json
{
  "origen": "Bogotá D.C.",
  "destino": "Cali",
  "distanciaTotal": 7,
  "ruta": [
    "Bogotá D.C.",
    "Medellín",
    "Cali"
  ]
}
```

---

## API Externa

Ciudades de Colombia:

https://api-colombia.com/api/v1/city

---

## Configuración SQL Server

application.properties

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=smart_network;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## Posibles Mejoras

* Persistencia completa de grafos.
* Swagger/OpenAPI.
  
---

## Autor

Luis David Gil Martinez
Miguel Angel Ospina Paez

Proyecto académico desarrollado para el estudio de estructuras de datos, grafos, algoritmos de caminos mínimos y desarrollo backend con Spring Boot.
