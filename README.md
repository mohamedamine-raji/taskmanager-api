# TaskManager REST API

A backend REST API developed using **Java, Spring Boot, PostgreSQL, and Maven**.

This project allows users to manage tasks through CRUD operations (Create, Read, Update, Delete).

## Technologies Used

- Java 25
- Spring Boot 4
- PostgreSQL
- Spring JDBC (JdbcTemplate)
- Maven
- Git & GitHub

## Features

- Create new tasks
- View all tasks
- Update task status
- Delete tasks
- Store tasks in a PostgreSQL database
- Validate task titles and statuses

## REST API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/tasks` | Get all tasks |
| POST | `/api/tasks` | Create a task |
| PUT | `/api/tasks/{id}` | Update task status |
| DELETE | `/api/tasks/{id}` | Delete a task |

## Example Task

```json
{
  "id": 1,
  "title": "Learn Java",
  "description": "Practice OOP",
  "status": "DONE"
}
```

Supported task statuses: `TODO`, `IN_PROGRESS`, `DONE`.

## Database Setup

Create a PostgreSQL database named `taskmanager_db` and execute:

```sql
CREATE TABLE tasks (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'TODO'
);
```

## How to Run

1. Clone the repository:

```bash
git clone https://github.com/mohamedamine-raji/taskmanager-api.git
cd taskmanager-api
```

2. Set up PostgreSQL and create the database and table.

3. Configure `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager_db
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
```

4. Set the PostgreSQL password as an environment variable.

5. Run the application on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

6. Open:

`http://localhost:8080/api/tasks`

## Future Improvements

- Add automated tests
- Add a service and repository layer
- Add a frontend interface
- Implement authentication

## Author

**Mohamed Amine Raji**

GitHub: https://github.com/mohamedamine-raji