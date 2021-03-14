# StorageSpace
REST API using Spring Boot, MySQL, Java 11 HttpClient.

Storage space is a fun project. The goal is to create a simple copy of Big Yellow Storage.


## Features of the application at the moment:

1. Find your nearest store in London
2. Select specific storage for your needs
3. Reserve a storage room
4. Get a storage price for specific room size
5. Get confirmation email with details of reservation

## Technologies Used in the application:

* Java
* Spring
* Hibernate
* Maven
* MySQL
* jUnit 5   
* Lombok  
* Mockito

## The future of the project
* Creating a frontend
* Add Spring Security

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/KonarzewskiP/StorageSpace.git
```

**2. Create Mysql database**
```bash
create database storage_management_system_db
```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Run the app using maven**

```bash
mvn spring-boot:run
```
The app will start running at <http://localhost:8080>

