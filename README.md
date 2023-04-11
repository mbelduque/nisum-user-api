# Nisum Test

Este proyecto es una API de creación de usuarios, desarrollada en Spring Boot 2.7.9, Java 17 y Maven. La API permite
crear usuarios junto con una lista de teléfonos. Los usuarios creados se almacenan en una base de datos H2 en memoria.

## Requerimientos

- [Java 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven](https://maven.apache.org/download.cgi)
- [Spring Boot 2.7.9](https://spring.io/projects/spring-boot)

## Instalación

1. Clonar el repositorio:

   `https://github.com/mbelduque/nisum-user-api.git`

2. Importar el proyecto en tu IDE de preferencia (por ejemplo, IntelliJ IDEA) desde el archivo

   `/nisum-user-api/pom.xml`

3. Compilar y empaquetar el proyecto con Maven, o desde tu IDE:

   `1. Maven: cd tu-repositorio mvn clean package`

   `2. IDE: Menú Maven -> userEntity-Api -> lifecycle / clean -> package -> install`

## Cómo probar la API

1. Iniciar la aplicación con Maven, o desde tu IDE:

   `1. Maven: mvn spring-boot:run`

   `2. IDE: Menú Run -> Run -> UserApiApplication`

2. Una vez que la aplicación se ha iniciado correctamente, podemos probar la API, utilizando alguna herramienta como
   Postman o curl. Por ejemplo, podemos enviar una solicitud POST al endpoint con el cuerpo JSON que contenga los datos
   del usuario que queremos crear. La respuesta debe incluir un código de estado HTTP 201 y los datos del usuario recién
   creado. (se adjunta en la raíz la colección de Postman)

3. También podemos probar las otras operaciones de la API (GET, POST) utilizando las URL correspondientes y los
   parámetros necesarios.

### URL's

La documentación de la API se puede acceder en la URL donde se encuentra la interfaz de Swagger para probar
los endpoints, igualmente para acceder a la base de datos y validar la persistencia se debe ingresar a través
de la consola de H2 

* Endpoint: [http://localhost:8080/api/users](http://localhost:8080/api/users)
* Swagger: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* H2 console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  
#### Credenciales H2 console
* usuario: nisum
* contraseña: nisum

### Validación de datos

La API valida los datos enviados en las peticiones de creación de usuarios. Sí se envían datos incorrectos o
incompletos, se recibirá una respuesta de error con un mensaje indicando cuál fue el problema.

1. Sí el email ya está registrado en la base de datos

`409 CONFLICT`

2. Sí el email tiene un formato incorrecto

`400 BAD REQUEST`

3. Sí la contraseña tiene un formato incorrecto

`400 BAD REQUEST`

## Endpoints

### Listar Usuarios

Obtiene la lista de usuarios y devuelve el estado `HTTP 200`, junto con los datos de los usuarios registrados.

**Endpoint**: `/api/users`

**Método HTTP**: `GET`

**Headers**:

- `Content-Type`: `application/json`

**Cuerpo de la respuesta**:

```json
[
  {
    "id": "string",
    "name": "string",
    "email": "string",
    "password": "string",
    "phones": [
      {
        "number": "string",
        "citycode": "string",
        "countrycode": "string",
        "userId": "string"
      }
    ],
    "created": "date",
    "modified": "date",
    "lastLogin": "date",
    "token": "string",
    "isActive": "boolean"
  }
]
```

### Crear Usuario

Crea un nuevo usuario y devuelve el estado `HTTP 201`, los datos del usuario recién creado, y campos adicionales.

**Endpoint**: `/api/users`

**Método HTTP**: `POST`

**Headers**:

- `Content-Type`: `application/json`

**Cuerpo de la petición**:

```json
{
  "name": "string",
  "email": "string",
  "password": "string",
  "phones": [
    {
      "number": "string",
      "citycode": "string",
      "countrycode": "string"
    }
  ]
}
```

**Cuerpo de la respuesta**:

```json
[
  {
    "id": "string",
    "name": "string",
    "email": "string",
    "password": "string",
    "phones": [
      {
        "number": "string",
        "citycode": "string",
        "countrycode": "string"
      }
    ],
    "created": "date",
    "modified": "date",
    "lastLogin": "date",
    "token": "string",
    "isActive": "boolean"
  }
]
```

## Diagrama de la solución

En el siguiente diagrama se muestra la estructura del proyecto y la arquitectura de la API:

![image](https://user-images.githubusercontent.com/19484378/227663382-4aface7d-d933-499c-8b95-35b8137600d3.png)

![image](https://user-images.githubusercontent.com/19484378/227664100-a439cf1a-9634-45ee-a8fe-caaeea847ec1.png)
