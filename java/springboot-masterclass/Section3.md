# Section 3 - Spring Framework

- Instead of building everything from scratch use frameworks for using boilerplates to build applications faster and better.
- Web framework:
  - Collection of tools and modules that is needed to do standard tasks across every web application faster.

## Core concepts of Spring Framework

### Loose coupling over Tight coupling

- Coupling refers to how closely connected different components or systems are
- Tight coupling: components are highly dependent on each other
- Loose coupling: components are less dependent on each other
- Achieve loose coupling:
  - interfaces and abstractions
  - dependency injection
  - event driven architecture

```java

// tight coupling
// Service is tightly coupled to DatabseA.
// If later, DatabaseA is changed to DatabaseB in the service implementation then we have to go to service class and update it
// Or create a new Service with DatabaseB

public class DatabaseA {
  public String getDetails() {}
}

public class Service {
  private DatabaseA db = new DatabaseA();

  public void getDetailsFromDB() {
    A.getDetails();
  }
}

public class Main {
  public static void main(String args[]) {
    Service s = new Service();
    s.getDetailsFromDB();
  }
}

// loose coupling
// Service can get any database implementation with the interface Database
// No need to change or create Service class. => Service and Database are loosely coupled

public interface Database {
  public String getDetails();
}

public class DatabaseA implements Database {
  @Override
  public String getDetails() {}
}

public class DatabaseB implements Database {
  @Override
  public String getDetails() {}
}

public class Service {
  private Database db;

  Service(Database db) {
    this.db = db;
  }

  public void getDetailsFromDB() {
    db.getDetails();
  }
}

public class Main {
  public static void main(String args[]) {
    Database db = new DatabaseA();
    Service s = new Service(db);
    s.getDetailsFromDB();

    Service s2 = new Service(new DatabaseB());
    s.getDetailsFromDB();
  }
}

```

### Dependency Injection (DI)

- Dependencies are not instantiated inside the class dependencies are used. Eg: service class doesn't create database (dependency)
- Instead dependencies are injected into the class at run time or whenever it is being used. Eg: In Main, we are injected DatabaseA as a dependency.

### Inversion of Control (IoC)

- Design principle where the control of object creation and lifecycle management is transferred from the application code to an external container.
- Spring framework takes care of DI using the IoC concept

### Beans

- Entities managed by framework are called beans
- Smallest entity

### Spring Container

- spring container manages the creation, management of objects
- 2 types:
  - ApplicationContext
  - BeanFactory
- How does spring know what objects it should manage?
  - via configuration files
  - contain bean definitions

## Spring Project

### Setting up spring

- add dependencies in pom.xml files
- spring core and spring context

```xml

<dependencies>

  <dependency></dependency>
  <dependency></dependency>

</dependencies>

```

- after changing pom.xml -> reload the changes using maven
- maven will fetch the dependencies and store it in external libraries
- with spring framework now we define beans and tell what is dependent on what.

### Creating a bean

- create a class to use eg: Car
- register it as bean by setting up an .xml file inside resources
- add a <bean></bean> template inside the .xml file
- give it an id (custom name) and class (where the definition of that bean is located)

### Using a bean

- initialize an ApplicationContext with the location of the .xml file
- `Car car = (Car) ac.getBean(id)`
- you have received the car object without creating it manually, but by instructing Spring IoC container to create it for you

### Beans and lifecycle

- step1: bean definition - class file
- step2: bean configuration - via xml files, annotations, etc.
- lifecycle: instantiate -> population of properties -> initialization -> ready to use -> destruction

### DI

- constructor injection
  - in xml define two beans and set the dependency of the 2nd bean with the 1st bean using constructor arg
  - constructor-arg tag
- setter injection
  - same as before but with property tag

### Autowiring

- wiring dependencies automatically without defining property or tags in xml
- types:
  - by name
    - mention in xml bean -> autoWire="byName"
    - it will match the name of the variable defined in the bean class with the configuration defined in xml
    - must have setter
  - by type
    - mention in xml bean -> autoWire="byType"
    - must have setter
  - by constructor
    - mention in xml bean -> autoWire="constructor"
    - must have a constructor

### Annotations

- add metadata in java to provide a metadata to your code
- feature of Java not springboot
- used heavily by spring and springboot

#### Component and ComponentScan

- Component - java class managed by Spring IoC container
- component vs bean
  - component is an autodetected bean
- define components
  - using xml
  - using Annotations `@Component`
- Component Scanning
  - Spring feature to help automatically detect and register beans from predefined package paths

```java
@Component("custom-name")
public class MyBean {}

public class App {
  public static void main(String[] args) {
    ApplicationContext ac = new ApplicationContext("component.xml");
    MyBean b = ac.getBean("custom-name", MyBean.class); // return the bean with typecasted to MyBean instead of Object
  }
}
```

```xml
<context:component-scan base-package="com.example.MyBean" />
```

- With component scanning and component marked, we don't have to create beans and tell spring about it for each bean.
- we define beans as components and tell spring to perform a component scan

@Value annotations to inject default values to data

```java
@Value("first")
private String name;
```

#### Transition from xml to annotations only

```java

@Component()
public class Employee{}

@Configuration()
@ComponentScan(basePackages = "com.example.components")
class AppConfig{}

public class App {
  public static void main(String[] args) {
    ApplicationContext ac = new AppConfigApplicationContext(AppConfig.class); // load the AppConfigApplicationContext with the AppConfig type
    Employee e = ac.getBean("employee", Employee.class);
  }
}

```

- no xml is used to configure.
- with application context, use the app config defined and get the beans

#### Autowiring with annotations

- `@Autowired` on top of fields -> field autowiring, on top of constructors -> constructor autowiring
- if more than one bean with same type found, then specify which one to use, using `@Qualifier('xyz')` annotation along with autowiring
- without autowiring we will specify the relationships between the beans
- with autowiring, we want spring to automatically detect the relationship
