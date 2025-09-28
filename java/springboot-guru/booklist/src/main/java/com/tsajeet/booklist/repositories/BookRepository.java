package com.tsajeet.booklist.repositories;

import com.tsajeet.booklist.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
