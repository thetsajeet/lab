# Junits Testing

- Testing is done to verify that the code works not only now but also in the future when new features are introduced.

## Test Runs

- Preparation
- Provide test inputs
- Run the test
- Provide expected outputs
- Verify result
- Do something to alert if test fails

> Frameworks like junits lets testers to run tests, verify result and do something if something failed via the framework

## Junit 4

- old and not upto date
- support only for monolithic architecture
- Junit 5 != Junit4 + 1

## Junit 5

- Platform: Junit uses this to run tests. Test engine
- Jupiter === Junit 5 API
- Vintage API to run Junit 4 tests
- Can have 3rd party APIs for writing tests
- User can choose any of the 3 to write unit tests using the platform
- IDE and tools can directly run tests through the platform but you can write code in jupiter, external, etc.

## Testing

- Java looks for main method when running a program. Similarly Jupiter looks for `@Test` method for running tests
- Api assertions like `assertEquals()`, `assertAll()`, etc.
- `fail()` fails the test no matter what

### TDD (Test driven development)

- write test cases first with input and output

### Surefire plugin

- run as maven command to run junit tests

## Test lifecycle

- Each method is run within a new instance of the test class
- BeforeAll, BeforeEach, AfterAll, AfterEach
- BeforeAll and AfterAll must be static methods

## Assumptions

- assumeTrue(value);
