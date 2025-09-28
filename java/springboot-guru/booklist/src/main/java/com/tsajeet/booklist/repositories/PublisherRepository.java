package com.tsajeet.booklist.repositories;

import com.tsajeet.booklist.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
