package com.tsajeet.booklist.services;

import com.tsajeet.booklist.domain.Author;

public interface AuthorService {
    public Iterable<Author> findAll();
}
