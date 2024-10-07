# Employee Tax Application

This is a Spring Boot application that connects to a local PostgreSQL 16 database running in a Docker container.

## Prerequisites

- **Java 17** or later
- **Maven**
- **Docker**

## Steps to Run the Application

### 1. Clone the Repository
- Clone the repository to your local machine and navigate into the project directory.

### 2. Set Up PostgreSQL Using Docker
- Create a `docker-compose.yml` file to define the PostgreSQL service.
- Start the PostgreSQL container using Docker.

### 3. Build the Application
- Use Maven to build the application.

### 4. Run the Application
- Launch the application using Maven.

### 5. Access the Application
- Open your web browser and navigate to `http://localhost:8080` to access the application.

### 6. Connect to the Database
- Use a PostgreSQL client to connect to the database with the specified credentials.

### 7. Stop the Database
- Use Docker commands to stop the PostgreSQL container when done.

## Troubleshooting
- Ensure the PostgreSQL container is running.
- Check application logs for any connection errors.
