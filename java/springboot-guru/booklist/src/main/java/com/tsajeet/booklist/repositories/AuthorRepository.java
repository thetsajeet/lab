package com.tsajeet.booklist.repositories;

import com.tsajeet.booklist.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
