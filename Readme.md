# Auth Task Service

A secure backend application built with Spring Boot that provides **JWT-based authentication**, **role-based access control**, and **task management APIs**, along with centralized exception handling, structured logging, and API documentation.


---

## 🚀 Features

### 🔐 Authentication & Authorization
- User registration and login
- Password hashing using BCrypt
- JWT-based stateless authentication
- Role-based access control (`USER`, `ADMIN`)
- Default admin user created on application startup

### 📝 Task Management
- Create, view, update, and delete tasks
- USER can manage only their own tasks
- ADMIN can manage all tasks
- Duplicate task prevention per user (same task title cannot be created twice)

### ⚠️ Exception Handling
- Centralized global exception handling using `@RestControllerAdvice`
- Custom error responses with:
 
- Custom handling for:
  - Validation errors (400)
  - Business errors (400)
  - Authentication errors (401)
  - Authorization errors (403)
  - Resource not found (404)
  - Internal server errors (500)

### 📊 Logging & Observability
- File-based logging using Logback
- Daily rolling log files
- MDC-based `traceId` for request-level log correlation
- Logs excluded from version control

### 📘 API Documentation
- OpenAPI / Swagger UI integration
- Interactive API testing support

### 🩺 Monitoring
- Spring Boot Actuator enabled
- Health and info endpoints exposed

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- Hibernate
- MySQL
- Lombok
- Logback

### API Documentation
- Springdoc OpenAPI (Swagger UI)

---

## 📂 Project Structure (High Level)
```
src/main/java
├── config # Security & OpenAPI configuration
├── controller # REST controllers (Auth, Task)
├── service # Business logic
├── repository # JPA repositories
├── entity # JPA entities (User, Task)
├── dto # Request & response DTOs
├── exception # Global & security exception handling
├── security # JWT filter, utilities
└── enums 

```

---

## ⚙️ How to Run the Application

### 1️⃣ Prerequisites
- Java 21
- Maven
- MySQL

### 2️⃣ Database Setup
Create a database in MySQL:
```sql
CREATE DATABASE auth_task_service;
Update database credentials in:

application-local.properties
```


3️⃣ Run the Application
```
mvn spring-boot:run
```

Application will start on:
```
http://localhost:8080
```

---

Authentication Flow
1️⃣ Register User
POST /api/v1/auth/register

2️⃣ Login
POST /api/v1/auth/login


Response:
```
{
  "accessToken": "<JWT_TOKEN>",
  "tokenType": "Bearer",
  "expiresAt": 1770374126000
}
```

3️⃣ Access Protected APIs

Include header:

Authorization: Bearer <JWT_TOKEN>

📝 Task APIs (Protected)

> Method Endpoint Description
```
POST	/api/v1/tasks	Create task
GET	/api/v1/tasks	Get tasks (USER: own, ADMIN: all)
PUT	/api/v1/tasks/{id}	Update task
DELETE	/api/v1/tasks/{id}	Delete task
```

🔒 Security Design

> Stateless JWT authentication

> No HTTP session usage

> Custom JWT authentication filter

> Custom handlers for:

> Unauthorized access (401)

> Forbidden access (403)

> Role checks and ownership validation enforced at service layer


📊 Logging

Logs are written to:

```
logs/auth-task-service.log
```

Each log entry includes a traceId for request tracing.

Log files are excluded from GitHub and shared separately as part of submission.



📘 Swagger UI

Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```


Public endpoints:

> Auth APIs

> Swagger documentation

> Actuator health/info

```
🩺 Actuator Endpoints

Health:

GET /actuator/health


Info:

GET /actuator/info

```
