# Introduction to spring framework

- Spring is a framework for building java based applications
- Spring is a collection of libraries like JDBC, Web, etc.
- Spring boot is a starter configuration template that quickly sets up Spring framework with lesser manual configurations
- Spring boot is a wrapper on top of spring framework

## Spring boot

- starter dependencies
- auto configurations
- externalized configurations
- logging auto configurations
- metrics

## Spring Projects

- spring data, spring cloud, spring security, etc.

## Spring web

- Springboot has an embedded tomcat server
- Host OS -> JVM -> embedded tomcat -> springboot -> controller -> service -> repository -> spring data jpa -> hibernate -> jdbc / sql -> h2 db -->....-> controller -> thymeleaf -> html
- default package (com.myproject.example) is the spring boot main class
- main class is annotated with @SpringBootApplication
- Spring boot will scan only the default package and child packages only
- Maven wrapper -> mvnw to run maven commands without installing maven locally
- Spring initializr to create spring boot projects

## Spring repo structure

- Called as Maven Standard Directory Layout
  > Read more about it online

```bash
springboot-app/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── README.md
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── example/
    │   │           └── demo/
    │   │               └── DemoApplication.java
    │   ├── resources/
    │   │   ├── application.properties   (or application.yml)
    │   │   ├── static/                  (CSS, JS, images)
    │   │   ├── templates/               (Thymeleaf, Freemarker templates)
    │   │   └── banner.txt               (optional Spring Boot banner)
    │   └── webapp/                      (optional: for JSP/WEB-INF if used)
    └── test/
        └── java/
            └── com/
                └── example/
                    └── demo/
                        └── DemoApplicationTests.java

```
