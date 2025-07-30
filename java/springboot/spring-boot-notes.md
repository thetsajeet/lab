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

## application.properties / application.yaml file to specify env variables for spring boot application

server.port = 9090

- can be overwritten by command line arguments
