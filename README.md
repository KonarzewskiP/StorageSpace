# StorageSpace
## Introduction

Storage Space is a REST application for managing storage space in multiple warehouses.

## Technology used
- Java version 17
- Spring Framework: The project utilizes the Spring Framework for building enterprise-level applications efficiently.
  - Spring Security: Enables you to secure your application by providing authentication and authorization features.
  - Spring Events: Allows you to implement event-driven architectures by leveraging the publish-subscribe pattern.
  - Spring MVC: Provides a powerful model-view-controller architecture for building web applications.

## The future of the project
* Implement front-end

## To run the application, follow these steps:

**1. Install Docker on your system if you haven't already.**

**2. Clone this repository to your local machine.**
```bash
git clone https://github.com/KonarzewskiP/StorageSpace.git
```

**3. Open a terminal or command prompt.**

**4. Navigate to the project's root directory.**

**5. Run the following command to start MySql Docker container:**
```bash
docker-compose up -d --build
```

**6. Run the following command to run application:**

```bash
mvn spring-boot:run
```

**7. Once the application starts, you can access it by navigating to http://localhost:8080 in your web browser.**


### POSTMAN Documentation:
https://documenter.getpostman.com/view/11183041/2s93z3e4yN#435dad39-9d3f-48e9-953f-c422c6b99cd7

#### To interact with API
1. register a new user with role ADMIN 
2. login by using your email (username) and password