package com.dut.bookstore.controller;

import com.dut.bookstore.model.Book;
import com.dut.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity getBooks() {
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity getBookById(@PathVariable long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteBook(@PathVariable long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>("Delete book is successfully!", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.updateBook(book), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity getBooksByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(bookService.getBooksByName(name), HttpStatus.OK);
    }
}
