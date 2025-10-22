# Spring R2DBC

- Reactive Relational Database Connectivity (R2DBC)
- early lifecycle unlike JDBC. So many features are not available like JDBC

## Setup

- Must configure R2DBC to setup with springboot

```java
package guru.springframework.spring6reactive.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class DatabaseConfiguration {
    @Value("classpath:/schema.sql")
    Resource resource;

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer connectionFactoryInitializer = new ConnectionFactoryInitializer();
        connectionFactoryInitializer.setConnectionFactory(connectionFactory);
        connectionFactoryInitializer.setDatabasePopulator(new ResourceDatabasePopulator(resource));
        return connectionFactoryInitializer;
    }

}

```

- No longer JPA annotations.
- Create a repository using ReactiveCrudRepository

```java
package guru.springframework.spring6reactive.repository;

import guru.springframework.spring6reactive.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {
}

```

- to enable auditing like createdDate, etc. use R2dbcAuditing annotation on data class
