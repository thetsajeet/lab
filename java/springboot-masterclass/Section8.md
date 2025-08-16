# Section 8 - JPA

- Jakarta Persistence API
- ORM that makes it easy to integrate with spring boot.
- Write native java methods to convert to SQL queries in the background

## H2 database

- used to mock real-life db like PostgreSQL
- in memory, fast and small sized
- can be viewed in the browser

## JPA

- @Entity to mark the POJO an entity with one column having an @Id will create a table in the jdbc connected database (h2)
- @GeneratedValue for ids -> strategy = Auto, identity, sequence,
- Auto => automatic strategy based on underlying database.
- identity -> identity column to generate a unique id
- sequence -> sequence_no for each record used with @SequenceGenerator()
- table -> stores next available value in a separate table (less efficient that sequence)

## Repository layer

- `interface CategoryRepository extends JPARepository<Category, Long> {}` - <Type of entity, Type of primary key>
- generates the implementation at run-time
- getters and setters are must for fields that need to be stored, and sent between DTOs
