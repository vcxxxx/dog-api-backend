# Dog API Backend

A Spring Boot REST API for managing dog breed information, built with Java 17 and JPA ORM.

## Live Demo

**API Endpoint:** https://dog-api-backend.onrender.com/api/dogbreeds

**Integrated Frontend Application:** https://dog-api-frontend.vercel.app

> **Note:** This backend is hosted on [Render](https://render.com) using the free tier. If inactive, it can take **2–3 minutes** to spin up after a period of inactivity.

## CORS Configuration

CORS is enabled to accept requests from:

- `http://localhost:3000` – for React development
- `https://dog-api-frontend.vercel.app` – deployed frontend

## Features

- RESTful API for managing dog breeds
- Persistent data using JPA with an in-memory or external DB
- Initial data loading from `dogs.json` if the database is empty
- Postman collection provided for easy testing
- Dockerized for deployment (Render)
- Unit tests included


## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Maven** for dependency management
- **Docker** for containerization

## Prerequisites

- Java 17 (Higher versions might not work with Lombok)
- Maven 3.6+

## Quick Start

### Local Development

1. **Clone the repository**
   ```bash
   git clone https://github.com/vcxxxx/dog-api-backend.git
   cd dog-api-backend
   ```

2. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the API**
   - Base URL: `http://localhost:8080`
   - Dog breeds endpoint: `http://localhost:8080/api/dogbreeds`

### DockerFile

This is included as a prerequisite for hosting on render, and has no impact on local development. 

## Testing

Run the unit tests with:
```bash
mvn test
```

### Postman Collection

A complete Postman collection is available in the `src/main/resources` directory. Import the JSON file into Postman to test all available endpoints.

## Database

The application uses JPA ORM for database operations. On first startup, the database is automatically populated with a curated list of dog breeds from the data file located in `src/main/resources`.

### Data Initialization

- Initial dog breed data is loaded from resources on application startup
- Database schema is auto-generated using JPA annotations
- No manual database setup required

## Deployment

The application is deployed on [Render](https://render.com) using the provided Dockerfile. The deployment automatically builds and runs the containerized application.

### Environment Variables

No additional environment variables are required for basic functionality. The application runs with default Spring Boot configurations.

## Reflection

This backend was designed with simplicity and clarity in mind. It demonstrates practical use of Spring Boot, JPA, and RESTful principles. 
The auto-initialization feature and provided Postman collection make it easy to test and deploy.
