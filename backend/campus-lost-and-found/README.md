# Campus Lost & Found

A REST API built with **Java 21 and Spring Boot** to help students manage lost and found items on campus. The API supports creating, viewing, updating, deleting, and searching for items, with PostgreSQL for data persistence.

## Features

- Create a lost or found item.
- Retrieve all items or find an item by ID.
- Update existing item details.
- Delete an item.
- Search items by status, name, category, and location.
- Combine multiple search filters.
- Validate incoming request data.
- Handle errors with structured JSON responses.
- Persist data in PostgreSQL using Spring Data JPA and Hibernate.
- Run automated tests for API behavior and request validation.

## Tech Stack

| Technology | Description |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.1.2 |
| Build Tool | Maven |
| Database | PostgreSQL |
| Persistence | Spring Data JPA / Hibernate |
| Testing | JUnit, Spring Boot Test, MockMvc, Mockito |

## Prerequisites

Before running the project, make sure you have the following installed:

- Java 21
- PostgreSQL
- Git
- Maven Wrapper (included in the project)

You should also have access to a PostgreSQL database user with permission to connect to the database and create or modify tables.

## Database Configuration

### 1. Create the Database

Open PostgreSQL and execute the following SQL statement:

```sql
CREATE DATABASE campus_lost_found;
```

### 2. Configure the Application

Open `src/main/resources/application.properties` and configure the database connection:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/campus_lost_found
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

**Important:** Set the `DB_PASSWORD` environment variable to your PostgreSQL password before starting the application.

Do not hardcode your actual database password in the source code or commit it to GitHub.

Hibernate automatically creates or updates the required database tables when the application starts.

> **Note:** The `spring.jpa.hibernate.ddl-auto=update` setting is suitable for local development. For production environments, consider using a database migration tool such as Flyway or Liquibase.

## Clone the Repository

```bash
git clone https://github.com/indreshps/dev-practice-projects
cd dev-practice-projects/backend/campus-lost-and-found
```

## Run the Application

### Windows Command Prompt (CMD)

**Step 1: Set the database password**

```cmd
set DB_PASSWORD=your_actual_password
```

Replace `your_actual_password` with your PostgreSQL password.

**Step 2: Start the application**

Run the following command from the project root directory:

```cmd
mvnw.cmd spring-boot:run
```

**Step 3: Access the API**

Once the application starts successfully, the API will be available at:

`http://localhost:8080`

Keep the same terminal session open while running the application so that the environment variable remains available.

### Verify the Application

You can test the API using Postman, curl, or another HTTP client.

For example, to retrieve all items:

```http
GET http://localhost:8080/api/items
```

## API Endpoints

The base URL for all endpoints is:

`http://localhost:8080`

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/api/items` | Create a new lost or found item |
| GET | `/api/items` | Retrieve all items or search using filters |
| GET | `/api/items/{id}` | Retrieve an item by ID |
| PUT | `/api/items/{id}` | Update an existing item |
| DELETE | `/api/items/{id}` | Delete an item |

## Search and Filtering

The `GET /api/items` endpoint supports optional query parameters for searching and filtering items.

| Parameter | Description | Example |
|---|---|---|
| `status` | Filter by item status (`LOST` or `FOUND`) | `?status=LOST` |
| `name` | Search by item name | `?name=wallet` |
| `category` | Filter by category | `?category=Accessories` |
| `location` | Search by item location | `?location=Library` |

### Combine Multiple Filters

You can combine multiple query parameters in a single request.

For example, to retrieve all found accessories:

```http
GET /api/items?status=FOUND&category=Accessories
```

To search for lost wallets near the library:

```http
GET /api/items?status=LOST&name=wallet&location=Library
```

### Search Behavior

- **Name:** Case-insensitive partial matching.
- **Location:** Case-insensitive partial matching.
- **Category:** Case-insensitive matching.
- **Status:** Case-insensitive matching.

Search parameters are optional, so you can use any supported combination of filters.

## Example Requests

### 1. Create an Item

**Endpoint:**

```http
POST /api/items
```

**Request Body:**

```json
{
  "name": "Black Wallet",
  "category": "Accessories",
  "color": "Black",
  "location": "College Library",
  "description": "A black wallet found near the reading area",
  "status": "FOUND"
}
```

**Field Descriptions:**

| Field | Description |
|---|---|
| `name` | Name of the lost or found item |
| `category` | Category to which the item belongs |
| `color` | Color of the item |
| `location` | Location where the item was lost or found |
| `description` | Additional details about the item |
| `status` | Item status: `LOST` or `FOUND` |

The `status` field must contain either `LOST` or `FOUND`.

The API validates incoming request data according to the validation rules implemented in the application.

### 2. Retrieve All Items

**Endpoint:**

```http
GET /api/items
```

Retrieves all items stored in the database.

### 3. Retrieve an Item by ID

**Endpoint:**

```http
GET /api/items/1
```

Retrieves the item with ID `1`, provided it exists.

### 4. Update an Item

**Endpoint:**

```http
PUT /api/items/1
```

Use a JSON request body containing the updated item details.

### 5. Delete an Item

**Endpoint:**

```http
DELETE /api/items/1
```

Deletes the item with ID `1`, provided it exists.

> **Note:** The example requests illustrate the API's intended usage. Actual response bodies, status codes, and validation requirements depend on the implemented controller and service logic.

## Error Handling

The API provides structured JSON error responses for common errors.

| HTTP Status | Description |
|---|---|
| `400 Bad Request` | The request contains invalid data or fails validation |
| `404 Not Found` | The requested item does not exist |

Error responses are designed to provide useful information about the failure.

The exact JSON response structure depends on the exception-handling implementation.

## Run Tests

The project includes automated tests for API behavior and request validation.

### Windows Command Prompt (CMD)

**Step 1: Set the database password if required by the test configuration**

```cmd
set DB_PASSWORD=your_actual_password
```

**Step 2: Run the tests**

```cmd
mvnw.cmd clean test
```

Maven compiles the project and executes the configured automated tests.

Review the test output to determine whether all tests pass.

Test reports are typically generated under:

```text
target/surefire-reports/
```

> **Note:** Whether the tests require a running PostgreSQL instance depends on the test configuration. Ensure any required database and environment settings are available before running them.

## Project Structure

```text
campus-lost-and-found/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── indresh/
│   │   │           └── campuslostfound/
│   │   │               ├── controller/
│   │   │               ├── exception/
│   │   │               ├── model/
│   │   │               ├── repository/
│   │   │               ├── service/
│   │   │               └── CampusLostAndFoundApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

### Package Responsibilities

| Package | Responsibility |
|---|---|
| `controller` | Handles HTTP requests and API responses |
| `exception` | Handles application exceptions and error responses |
| `model` | Contains entity classes representing application data |
| `repository` | Provides database access using Spring Data JPA |
| `service` | Contains application business logic |

`CampusLostAndFoundApplication.java` is the main entry point for the Spring Boot application.

## Learning Goals

This project provides practical experience with:

- Designing and implementing REST APIs.
- Building backend applications with Java and Spring Boot.
- Integrating PostgreSQL with Spring Data JPA and Hibernate.
- Implementing CRUD operations.
- Validating incoming request data.
- Handling exceptions and returning structured error responses.
- Implementing dynamic search queries and combining filters.
- Writing automated tests with JUnit, Mockito, Spring Boot Test, and MockMvc.
- Managing application configuration through environment variables.
- Using Maven to build, test, and run a Java application.

## Future Improvements

Potential enhancements include:

- **Pagination and sorting:** Improve performance and usability when retrieving large numbers of items.
- **Authentication and authorization:** Secure the API and introduce role-based access control.
- **Frontend application:** Provide a user-friendly interface for students to report and search for lost items.
- **Image uploads:** Allow students to attach pictures of lost or found items.
- **Claim management:** Enable students to submit claims for found items.
- **Additional automated tests:** Expand coverage for search filters, error handling, and edge cases.
- **API documentation:** Integrate Swagger UI or OpenAPI documentation.
- **Database migrations:** Use Flyway or Liquibase to manage schema changes.
- **Deployment:** Deploy the application to a cloud platform and configure a production database.

## Project Status

**Status:** Backend implementation and initial automated tests completed.

The project currently focuses on REST API development, database integration, CRUD operations, search and filtering, input validation, exception handling, and automated testing.

## Author

**Indresh**

Developed as a backend project to practice Java, Spring Boot, REST API development, PostgreSQL, and automated testing.
