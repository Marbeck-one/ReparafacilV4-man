
# ⚙️ ReparaFácil SPA - Backend API

> **Asignatura:** DESARROLLO FULLSTACK II_001D

## 📖 Descripción del Proyecto

Este repositorio contiene el **Backend (API RESTful)** de la plataforma **ReparaFácil SPA**. Su función principal es gestionar la lógica de negocio, la persistencia de datos y la seguridad de la aplicación.

Está construido sobre **Spring Boot** y expone endpoints seguros para que el Frontend (React) pueda realizar operaciones de gestión de usuarios, servicios, agendas y mensajería.

## 🚀 Características Principales

* **🔐 Seguridad Robusta:** Implementación de **Spring Security** con **JWT (JSON Web Tokens)** para autenticación y autorización stateless.
* **👤 Roles y Permisos:** Control de acceso granular para `ADMIN`, `TECNICO` y `CLIENTE`.
* **💾 Persistencia de Datos:** Base de datos **H2** embebida (en modo archivo) para facilitar el despliegue y pruebas sin configuraciones externas complejas.
* **📄 Documentación API:** Integración con **Swagger / OpenAPI 3** para visualizar y probar los endpoints interactivamente.
* **💬 Chat y Mensajería:** Lógica de negocio para persistir conversaciones entre usuarios.
* **📅 Gestión de Agenda:** Control de disponibilidad y reservas para técnicos.

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java 17 (OpenJDK).
* **Framework:** Spring Boot 3.2.11.
* *Spring Web (MVC REST)*
* *Spring Data JPA (Hibernate)*
* *Spring Security*
* *Validation*


* **Base de Datos:** H2 Database (Archivo local).
* **Herramientas:**
* **Maven:** Gestión de dependencias.
* **Lombok:** Reducción de código repetitivo (Boilerplate).
* **SpringDoc OpenAPI:** Documentación automática de la API.



## ⚙️ Pre-requisitos

1. **Java JDK 17** instalado y configurado en las variables de entorno (`JAVA_HOME`).
2. **Maven** (Opcional, el proyecto incluye `mvnw` wrapper).
3. Puerto **8082** libre (puerto por defecto configurado en `application.properties`).

## 💻 Instalación y Ejecución

Sigue estos pasos para levantar el servidor backend:

### 1. Clonar el repositorio

```bash
git clone [URL_DEL_REPO_BACKEND]
cd ReparafacilV2

```

### 2. Ejecutar la aplicación

Puedes usar el wrapper de Maven incluido para descargar dependencias y ejecutar:

**En Windows:**

```cmd
mvnw.cmd spring-boot:run

```

**En Linux/Mac:**

```bash
./mvnw spring-boot:run

```

Una vez iniciado, verás logs indicando que el servidor corre en el puerto 8082.

---

## 🔌 Endpoints y Documentación (Swagger)

La API cuenta con documentación interactiva generada automáticamente. Una vez levantado el servidor, accede a:

👉 **[http://localhost:8082/doc/swagger-ui.html](https://www.google.com/search?q=http://localhost:8082/doc/swagger-ui.html)**

Desde ahí podrás probar los endpoints de:

* `/api/auth` (Login y Registro)
* `/api/clientes`
* `/api/tecnicos`
* `/api/servicios`
* `/api/garantias`
* `/api/agendas`
* `/api/mensajes`

---

## 🔑 Usuarios por Defecto (Data Seeder)

El sistema incluye una clase `DataSeeder.java` que precarga datos útiles para pruebas al iniciar la aplicación. Puedes usar estas credenciales para loguearte inmediatamente:

| Rol | Usuario (Username/Email) | Contraseña |
| --- | --- | --- |
| **Administrador** | `admin` | `admin123` |
| **Técnico** | `tecnico1` | `123456` |
| **Cliente** | `cliente1` | `123456` |
| **Técnico (Perfil)** | `carlos.perez@reparafacil.com` | (Contraseña asignada en creación) |

> **Nota:** La base de datos H2 guarda los datos en la carpeta `./data/reparafacilV2`. Si deseas reiniciar los datos a cero, puedes borrar esa carpeta o los archivos `.db` dentro de ella.

## 📂 Estructura del Proyecto

```text
com.Reparafacil.ReparafacilV2
├── config/          # Configuraciones (DataSeeder, etc.)
├── controller/      # Controladores REST (Endpoints)
├── dto/             # Data Transfer Objects (AuthRequest, etc.)
├── exception/       # Manejo global de excepciones
├── model/           # Entidades JPA (Tablas de BD)
├── repository/      # Interfaces Repositorios (JPA)
├── security/        # Configuración JWT y Filtros
└── service/         # Lógica de negocio (Interfaces e Impl)

```

## 🤝 Relación con el Frontend

Este Backend está diseñado para trabajar conjuntamente con el Frontend en React. Asegúrate de configurar la URL base en el Frontend (archivo `.env`) para que apunte a este servidor:

```env
VITE_API_URL=http://localhost:8082/api

```

## 👤 Autores

Proyecto desarrollado por el equipo de **ReparaFácil** para la asignatura de Desarrollo Fullstack II.

En especial:

* **MARBECK-ONE (BECKER)**
