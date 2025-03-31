# Hospital Management System

## Descripción
Este proyecto es un sistema de gestión hospitalaria desarrollado con **Spring Boot** y **Java**, utilizando **MongoDB** como base de datos. Permite la administración de **pacientes**, **doctores** y **citas médicas (appointments)**, manejando relaciones entre estas entidades de manera eficiente. Además, mandará un correo tanto al doctor como al paciente cuando se cree o se modifique una cita cualquiera, informando en todo momento de las modificaciones.
Para probar el sistema se tiene la colección de Postman.

## Tecnologías Utilizadas
- **Java 17+**
- **Spring Boot** (Spring Data, Spring Web, Spring Validation)
- **MongoDB** como base de datos NoSQL
- **Maven** para la gestión de dependencias
- **Lombok** para reducir código boilerplate
- **Swagger** para la documentación de la API

## Arquitectura del Proyecto
El sistema sigue una arquitectura basada en capas:

1. **Rest**: Gestiona las solicitudes HTTP y responde con datos en formato JSON.
2. **Service**: Contiene la lógica de negocio.
3. **Repository**: Se comunica con MongoDB mediante Spring Data.
4. **Model (Entidades)**: Representan los datos almacenados en la base de datos.
5. **DTOs (Data Transfer Objects)**: Controlan la información expuesta por la API para evitar referencias cíclicas.

## Instalación y Configuración
### 1. Clonar el repositorio
```sh
 git clone https://github.com/tu_usuario/hospital-management.git
 cd hospital-management
```

### 2. Configurar MongoDB
El sistema requiere una instancia de **MongoDB** en ejecución. Puedes utilizar Docker para iniciarla fácilmente, para ello
se ha generado un fichero dockerfile.yml que al arrancarlo arrancará un container de MongoDB para su uso en el puerto 27017

### 3. Configurar el archivo `application.properties`
Ubicado en `src/main/resources/application.properties`:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/hospital_db
configurar el correo y contraseña de aplicación para permitir mandar correos
```

### 4. Construcción y Ejecución
```sh
mvn clean install
mvn spring-boot:run
```

El servidor se ejecutará en `http://localhost:8080`.

## Endpoints de la API
La API expone los siguientes endpoints:

### Pacientes
- **GET** `/api/v1/patients` → Obtiene todos los pacientes
- **POST** `/api/v1/patients` → Crea un nuevo paciente
- **GET** `/api/v1/patients/{id}` → Obtiene un paciente por ID
- **PUT** `/api/v1/patients` → Actualiza un paciente
- **DELETE** `/api/v1/patients/{id}` → Elimina un paciente

### Doctores
- **GET** `/api/v1/doctors` → Obtiene todos los doctores
- **POST** `/api/v1/doctors` → Crea un nuevo doctor
- **GET** `/api/v1/doctors/{id}` → Obtiene un doctor por ID
- **PUT** `/api/v1/doctors` → Actualiza un doctor
- **DELETE** `/api/v1/doctors/{id}` → Elimina un doctor

### Citas Médicas (Appointments)
- **GET** `/api/v1/appointments` → Obtiene todas las citas
- **POST** `/api/v1/appointments` → Crea una nueva cita
- **GET** `/api/v1/appointments/{id}` → Obtiene una cita por ID
- **PUT** `/api/v1/appointments` → Actualiza una cita
- **DELETE** `/api/v1/appointments/{id}` → Elimina una cita

## Documentación con Swagger
La API incluye documentación generada con **Swagger**. Puedes acceder a ella en:
`http://localhost:8080/swagger`

## Licencia
Este proyecto está bajo la licencia MIT. Puedes usarlo y modificarlo libremente.

---
🚀 **¡Gracias por contribuir y usar Hospital Management System!**


