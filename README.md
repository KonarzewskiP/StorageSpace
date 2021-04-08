# StorageSpace

Storage Space is a REST application for renting storage space in different warehouses.
It is built with Java, Spring Boot, and Spring Framework. 
It enables to reserve a storage room at the specific warehouse and receive a quotation for a chosen room.

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
* Maven
* Hibernate
* MySQL
* jUnit 5
* Mockito
* Lombok  
* Swagger

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
The app will start running at <http://localhost:8080>. You can test it in Postman or Swagger at <http://localhost:8080/swagger-ui.html>

## Explore Rest APIs

The app defines following CRUD APIs.

### Warehouse

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST    | /warehouses | Add warehouse | [JSON](#warehousecreate)|
| GET    | /warehouses/{id} | Get warehouse by id | |
| GET   | /warehouses | Get all warehouses | |
| GET    | /warehouses/{id}/available | Get not reserved storage rooms in the specific warehouse by warehouse id | |

### User

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST    | /users | Add user | [JSON](#usercreate)|
| GET    | /users/{id} | Get user by id | |

### StorageRoom

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| PUT    | /storages | Update storage room | [JSON](#updateStorageRoom)|
| GET    | /storages/{id} | Get storage room by id | |

### Quote

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST    | /quote | Send quotation for user | [JSON](#quotepost)|

### Postcode

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /postcodes/{postcode} | Check if postcode is valid ||
| GET    | /postcodes/{postcode}/nearest | Get ordered warehouses by distance from the postcode | |


## Sample Valid JSON Requests

##### <a id="warehousecreate">Add warehouse -> /warehouses</a>
```json
{
  "city": "London",
  "name": "Blue Box",
  "postCode": "SW8 3NS",
  "street": "Ingate Place"
}
```
##### <a id="usercreate">Add user -> /users</a>
```json
{
  "email": "test@email.com",
  "firstName": "Joe",
  "gender": "MALE",
  "lastName": "Smith",
  "role": "CUSTOMER"
}
```
##### <a id="updateStorageRoom">Update storage room -> /storages</a>
```json
{
  "endDate": "2023-04-03",
  "id": 2,
  "reserved": true,
  "startDate": "2023-03-03"
}
```
##### <a id="quotepost">Send quotation-> /quote</a>
```json
{
  "duration": "LESS_THAN_2_WEEKS",
  "email": "steveKaczka@gmail.com",
  "extraServices": [
    "EXTENDED_HOURS",
    "FORKLIFTING",
    "ACCEPTING_DELIVERIES"
  ],
  "firstName": "James",
  "postcode": "E2 6HN",
  "size": "TELEPHONE_BOX",
  "startDate": "2022-10-12",
  "surname": "Cook",
  "type": "BUSINESS",
  "warehouseName": "Wapping"
}
```