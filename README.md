# literAlura

Catálogo y buscador de libros por consola en Java utilizando Spring Boot y PostgreSQL. Este proyecto interactúa mediante consumo HTTP con la API de [Gutendex](https://gutendex.com/) para buscar libros por título, procesar la respuesta en formato JSON e insertarla de forma relacional (Libros y Autores) dentro de una base de datos local para su posterior consulta a través de un menú interactivo.

## 🚀 Estado del Proyecto
🚧 **En desarrollo:** Faltando implementar el llamado HTTP final de búsqueda de libros hacia la API en la opción 1. Las lógicas de repositorio local y estructura relacional ya se encuentran completadas.

## 🛠️ Tecnologías Usadas
* **Java 17:** Lenguaje de desarrollo.
* **Spring Boot (3.x):** Framework base para la inyección de dependencias, automatización y creación de la aplicación de consola (`CommandLineRunner`).
* **Spring Data JPA & Hibernate:** ORM y persistencia de datos.
* **PostgreSQL:** Motor de base de datos relacional nativo usado para el almacenamiento histórico.
* **Jackson (ObjectMapper):** Procesamiento y deserialización de las tramas de respuesta JSON.
* **Gutendex API:** Fuente de datos externa pública y abierta alimentada por el Proyecto Gutenberg.
* **Lombok:** Reducción de código repetitivo en Entidades y Records Java (Getters, Setters, Contructores).
* **Maven:** Gestor de construcción y dependencias.

## ⚙️ Cómo ejecutar el proyecto

### 1. Requisitos Previos
* Tener instalado **Java JDK 17** o superior.
* Tener instalado **PostgreSQL** y asegurarse de que el servidor esté corriendo (normalmente en el puerto `5432`).
* Poseer **Maven** (o se puede utilizar el *wrapper* incluido `./mvnw`).

### 2. Configurar la Base de Datos
Debes crear una base de datos local en PostgreSQL llamada `literalura`.
Desde tu gestor o línea de comandos SQL (psql):
```sql
CREATE DATABASE literalura;
```

Asegúrate de que tus credenciales de PostgreSQL concuerden con las estipuladas en el archivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=postgres
```
*Si tienes otra contraseña para el usuario postgres, modifícala ahí.*

### 3. Compilación y Ejecución
Abre una terminal en el directorio raíz del proyecto (donde se ubica el `pom.xml`) y ejecuta:

**En Windows:**
```powershell
.\mvnw clean compile
.\mvnw spring-boot:run
```

Si cuentas con un IDE como IntelliJ IDEA, VS Code o Eclipse, puedes simplemente darle a "Run" a la clase principal `LiterAluraApplication.java`.
JPA (Hibernate) creará automáticamente las tablas `autores` y `libros` al levantar el sistema.

## 📖 Funcionalidades del Menú
Una vez arranques la aplicación, verás en la consola del terminal un menú como este:

1. **Buscar libro por título:** Consulta en tiempo real la API de Gutendex, procesa el JSON recibido y guarda en base de datos. (*En Construcción*).
2. **Listar libros registrados:** Recupera todos los libros almacenados en tu BD y formatea la salida en consola, indicando su título, autor, idioma y total de descargas.
3. **Listar autores registrados:** Lista todos los autores de los cuales tienes libros, e incluye en cada uno un arreglo con los libros suyos que tienes descargados.
4. **Listar autores vivos en un año:** Te pide que introduzcas un año (ej. 1600) y busca en tu BD aquellos autores cuyo año de nacimiento fue menor al año provisto y sumado a que su fecha de fallecimiento es mayor.
5. **Listar libros por idioma:** Ofrece un submenú (es, en, fr, pt) guiándote a filtrar todos los libros disponibles localmente que correspondan a ese lenguaje.

### Ejemplo de uso (Opción 2)
```text
Elija una de las siguientes opciones:
1 - Buscar libro por título
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un año
5 - Listar libros por idioma

0 - Salir
********************************
Ingrese su opción: 2

--- LIBROS REGISTRADOS ---
------------- LIBRO -------------
Título: Don Quijote de la Mancha
Autor: Miguel de Cervantes Saavedra
Idioma: es
Número de descargas: 12567
---------------------------------

------------- LIBRO -------------
Título: Romeo and Juliet
Autor: William Shakespeare
Idioma: en
Número de descargas: 35210
---------------------------------
```

## 📂 Estructura del Proyecto
El código sigue las convenciones de separación de responsabilidades de un ecosistema MVC/Servicios convencional:

* `com.tuforo.literalura`
  * `.dto` : Contiene las clases `Record` como `AutorDTO`, `LibroDTO` exclusivas para interceptar la respuesta Jackson.
  * `.model` : Define las entidades JPA que mapean contra tu motor de base de datos (`Autor` y `Libro`).
  * `.repository` : Interfaces *Spring Data JPA* exclusivas para conectarse y armar sentencias personalizadas (`AutorRepository` y `LibroRepository`).
  * `.service` : Soporte en la lógica de conversión JSON (`ConvierteDatos`) e interacción con `HttpClient` para conectar al Exterior (`ConsumoAPI`).
  * `.principal` : Clase `CommandLineRunner` encargada de iterar el menú y hacer de pivote transaccional.

## ✨ Autor y Agradecimientos
Proyecto desarrollado como práctica introductoria a *Spring Boot* y mapeos de Bases de Datos Relacionales consumiendo APIs externas.
Agradecimientos especiales al equipo del proyecto Gutenberg que matiene en marcha gratuita la API detrás de *Gutendex*.
