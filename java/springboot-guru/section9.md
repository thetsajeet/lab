# Spring Data JPA

- JPA -> Java persistence API (standard)
- Hibernate -> implementation of JPA
- Spring data JPA -> Spring data jpa on top of hibernate as default provider, can work with other providers

## DTOs

- Data transfer objects
- Simple POJOs, should not have behavior
- From one component to another component use DTOs
- Entities can leak data to client, so don't use it to pass from service to upper layers
- DTOs are optimized for serialization and deserialization.
- MapStruct is a code generator that automatically generates implementation

## Mapper

## Transactional and Rollback
