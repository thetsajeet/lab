# Spring Data with MongoDB

- to setup mongodb with springboot:
  - use the required dependency
  - create @Configuration for MongoConfig
  - create a mongoClient using `MongoClients.create()`
  - override method for `getDatabaseName()`
  - override method for `configureClientSettings()` to connect to MongoDB with auth
- Entities should be mapped as `@Document` not `@Entity` in mongodb.
- repository is same
- use `awaitility.await().untilTrue()` to wait for subscriptions to complete.
- avoid using findBy, use findFirstBy
- can add docker-compose to start during springboot starts -> add dependency in pom.xml
