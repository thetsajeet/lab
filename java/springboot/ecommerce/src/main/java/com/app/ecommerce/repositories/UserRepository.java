package com.app.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ecommerce.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // This interface will automatically provide CRUD operations for User entities
    // No additional methods are needed unless custom queries are required    
}
