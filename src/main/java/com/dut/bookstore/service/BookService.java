package com.dut.bookstore.service;

import com.dut.bookstore.model.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    Book getBookById(long id);
    List<Book> getBooks();
    void deleteBookById(long id);
    Book updateBook(Book book);
    List<Book> getBooksByName(String name);
}
