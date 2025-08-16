# Section 4 - Annotations

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
