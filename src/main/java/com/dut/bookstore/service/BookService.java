package com.dut.bookstore.service;

import com.dut.bookstore.model.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    Book getBookById(int id);
    List<Book> getBooks();
    void deleteBookById(int id);
    Book updateBook(Book book);
    List<Book> getBooksByName(String name);
}
