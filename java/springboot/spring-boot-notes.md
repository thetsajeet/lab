# Spring boot

- Setting up spring for production ready was difficult:
  - Dependency Management: Manage dependencies and versions in pom.xml
  - Configure .xml for spring framework
  - Define and manage spring configuration
  - Manual implementation of NFR -> logging, error handling, monitoring
- Creating a spring boot project from spring initializer
  - group -> package name
  - artifact -> class

## Spring Boot production ready features

- Initializer
- Starter project
- Auto configuration
- Dev Tools
- Logging
- Monitoring (Spring boot actuator)

## REST API

- RestController for the class
- RequestMapping for the API methods
- @GetMapping
- @PostMapping
- to return as a proper response: `ResponseEntity<>(service.getAll(), HttpStatus.OK)`
- or: `ResponseEntity.ok()`
- `@RequestMapping` annotation can be used at controller or method level.

## application.properties / application.yaml file to specify env variables for spring boot application

`server.port = 9090`

- can be overwritten by command line arguments

## JPA - Jakarta Persistence API

without JPA -> create connection, statement, executeQuery
with JPA -> `User user = userRepo.findById(1L).orElse(null);` sql-like syntax

To mark as entity -> @Entity
Requires default constructor to create entities

@Repository decorator with UserRepository interface that extends JpaRepository<User, Long> to specify that it's a repository

## Flow thus far

1. Create a spring boot application using spring.boot.initializer
2. Run the spring boot project using maven
3. Create controllers using @RestController and endpoints using @RequestMapping
4. Create services using @Service and use it in controllers
5. Create models using @Data (lombok)
6. Create repositories using @Repository and extend from JpaRepository<Model, type of primary key>
