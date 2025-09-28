package com.tsajeet.booklist.services;

import com.tsajeet.booklist.domain.Book;

public interface BookService {
    Iterable<Book> findAll();
}
