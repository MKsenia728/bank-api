# Bank API

REST API for managing clients, accounts, and managers in a simplified banking system.

## Tech Stack

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Liquibase
* MapStruct
* Swagger / OpenAPI
* JUnit / Mockito
* MySQL

## Features

* Create and manage bank accounts
* Retrieve accounts by ID or IBAN
* Filter accounts by status
* Manage clients and managers
* Validate request data using custom annotations

## Project Structure

The application follows a layered architecture:

* controller — REST endpoints
* service — business logic
* repository — data access layer
* dto — data transfer objects
* entity — database entities
* mapper — entity–DTO mapping (MapStruct)

## Validation

Custom validation is implemented for:

* UUID
* IBAN
* Positive numeric values
* Enum-based request parameters

## Running the Project

1. Clone the repository
2. Configure database properties via environment variables
3. Run Liquibase migrations
4. Start the application

## API Documentation

Swagger UI is available at:
`http://localhost:8080/swagger-ui/index.html`

## Tests

The project includes tests for:

* Controller layer
* Service layer
* Mapper layer

## Database

Detailed database structure is described in:
`/docs/database.md`

## Future Improvements

* Add Spring Security (authentication & authorization)
* Implement pagination and sorting
* Improve global exception handling
* Add Docker support

