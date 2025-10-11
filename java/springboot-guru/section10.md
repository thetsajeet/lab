# Section 10 - Data Validations

- trust but verify
- verify data at every exchange

## Data Validations

- Java Bean Validation - Java API standard
- Null, NonNull, Size, etc.

## Enabling validations

- update pom.xml
- update DTO with validation annotations on properties
- update controller signature to add @Validated in the params
- Handle custom exception handler to send appropriate response to client
- when testing validations with JPA repositories, in the test case -> use `beerRepo.flush()` after creating a new repository. By default hibernate delays, writing changes to the database, but test cases might run before that giving false signals for success. So immediately flush the database, any errors when validating before writing to the database are captured accurately.
- when column validations are performed, database throws sql exceptions (not good for user experience). use appropriate JPA validations along with Column to get better validation errors
- JPA validation at repository level should be handled by Controller using TransactionSystemException
