# Section 5 - Introduction to spring boot

- Spring boot is an opensource, spring-based framework used to generate production-grade applications with very little boilerplate code and autoconfigurations

## Spring vs Spring boot

- with spring, set up local server, configurations, write boilerplate code
- springboot, pre-configured defaults, eliminate boilerplate code, and run with the embedded servers.
- springboot = spring + prebuilt configs + embedded servers

## Components of spring boot

- spring boot starters: eg: web starter, event driven starter
- auto configuration
- spring boot actuator: monitor the application like health checks, readiness
- embedded server: run application as an executable of jar file instead of deploying somewhere to run the application
- springboot dev tools: better logging, auto restart

## Springboot architecture

- 3 layers: presentation, service and data access layer
- presentation: where endpoints are setup
- service: where business logic is setup
- data access: interact with the database
- browser -> controller -> service -> repository -> database

## Spring boot

- create spring boot projects using spring initializer
- to map endpoints use @RestController (combination of @Controller and @ResponseBody) on the class
- on the methods, use @GetMapping
- dispatcherServlet matches incoming request to the appropriate method
- when springboot returns objects instead of primitives it wil automatically convert to JSON object using Jackson library
- to return the properties in the object, have getters for it defined in the class
- Jackson library is also responsible for converting incoming data into into json request

## application.properties

- file to customize the spring boot application
- eg: logging, port, etc
