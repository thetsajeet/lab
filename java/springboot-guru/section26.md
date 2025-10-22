# Reactive Programming with Spring

- Reactive manifesto states that systems should be:
  - responsive
    - consistent response times, respond timely
  - elastic
    - stays responsive under varying workload
  - resilient
    - stays responsive even at the event of failure
  - message driven
    - asynchronous non-blocking
- Reactive programming is a tool to building reactive systems
  - reactive can improve computing efficiency
  - typical CRUD type application will not see much improvement
- Reactive programming is an asynchronous programming paradigm focused on streams of data
- features:
  - data streams
  - asynchronous
  - non-blocking
  - backpressure
  - failures as messages
- events are captured asynchronously
- non-blocking will process available data, to be notified when more is available
- backpressure: ability of subscriber to throttle data
- failures as messages: exceptions are handled by a handler function, and captured as events and shown as messages
- performance benefit is more apparent when a system in under load

## Reactive Streams API

- `Publisher`
- `Subscriber`
- `Subscription`
- `Processor`
- `mono` -> zero or one elements in data stream
- `flux` -> zero or many elements in data stream

## Difference between blocking and non-blocking components in spring

- spring-mvc vs spring-webflux
- blocking vs non-blocking
- servlet api vs http / reactive streams
- servlet container vs tomcat, jetty, netty, undertow
- dependencies: react spring web not spring web

## Code samples

```java
    public class PersonRepositoryImpl implements PersonRepository {

        Person p1 = Person.builder().id("1").firstName("John").lastName("Doe").build();
        Person p2 = Person.builder().id("2").firstName("Jane").lastName("Smith").build();
        Person p3 = Person.builder().id("3").firstName("Jim").lastName("Beam").build();

        @Override
        public Mono<Person> getById(String id) {
            return Mono.just(p1);
        }

        @Override
        public Flux<Person> findAll() {
            return Flux.just(p1, p2, p3);
        }
    }

    void getById_Blocking() {
        Mono<Person> personMono = new PersonRepositoryImpl().getById("1");
        Person person = personMono.block();
        assertNotNull(person);
        System.out.println("Person: " + person.toString());
    }

    void getById_Subscriber() {
        Mono<Person> personMono = new PersonRepositoryImpl().getById("1");
        personMono.subscribe(System.out::println);
    }

    void getById_MapAndSubscribe() {
        Mono<Person> personMono = new PersonRepositoryImpl().getById("1");
        personMono.map(Person::getFirstName).subscribe(System.out::println);
    }

    void findAll() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.subscribe(System.out::println);
    }

    void getOneByFilter() {
      Mono<Person> person = personRepository.findAll().filter().next(); // get's the first mono satisfying the condition
    }
```

- variables passed inside functions like filter must be constants (final)
-
