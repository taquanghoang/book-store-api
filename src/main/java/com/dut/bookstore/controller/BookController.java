package com.dut.bookstore.controller;

import com.dut.bookstore.constant.DefaultParam;
import com.dut.bookstore.model.Book;
import com.dut.bookstore.model.BookImage;
import com.dut.bookstore.repository.BookImageRepository;
import com.dut.bookstore.service.BookService;
import com.dut.bookstore.service.ImageService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RestController
@CrossOrigin
@RequestMapping("api/books")
public class BookController {
    private final BookService bookService;
    private final BookImageRepository bookImageRepository;
    private final ImageService imageService;

    public BookController(BookService bookService, BookImageRepository bookImageRepository, ImageService imageService) {
        this.bookService = bookService;
        this.bookImageRepository = bookImageRepository;
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity getBooks() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(bookService.getBooks());
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("filter.Book", SimpleBeanPropertyFilter.serializeAll())
                .addFilter("filter.Author", SimpleBeanPropertyFilter.serializeAllExcept("book"))
                .addFilter("filter.BookImage", SimpleBeanPropertyFilter.serializeAllExcept("book"));
        mappingJacksonValue.setFilters(filterProvider);
        return new ResponseEntity<>(mappingJacksonValue, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity getBookById(@PathVariable int id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addBook(@RequestParam String bookString, @RequestParam MultipartFile[] files) {
        Gson g = new Gson();
        Book book = g.fromJson(bookString, Book.class);
        Book finalBook = bookService.addBook(book);
        Arrays.asList(files).stream().forEach(file -> {
            BookImage bookImage = new BookImage();
            bookImage.setBook(finalBook);
            bookImage.setUrl(imageService.upload(file, DefaultParam.BOOK, finalBook.getId()));
            bookImageRepository.save(bookImage);
        });
        return new ResponseEntity<>("Create Book Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteBook(@PathVariable int id) {
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
