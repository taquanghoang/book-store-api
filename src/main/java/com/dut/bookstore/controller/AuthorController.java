package com.dut.bookstore.controller;

import com.dut.bookstore.constant.DefaultParam;
import com.dut.bookstore.model.Author;
import com.dut.bookstore.repository.AuthorRepository;
import com.dut.bookstore.service.ImageService;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("api/author")
public class AuthorController {

    public final AuthorRepository authorRepository;
    public final ImageService imageService;

    public AuthorController(AuthorRepository authorRepository, ImageService imageService) {
        this.authorRepository = authorRepository;
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity addAuthor(@RequestParam String authorString, @RequestParam MultipartFile file) {
        Gson g = new Gson();
        Author author = g.fromJson(authorString, Author.class);
        author = this.authorRepository.save(author);
        author.setAvatar(imageService.upload(file, DefaultParam.AVATAR, author.getId()));
        this.authorRepository.save(author);
        return new ResponseEntity("Create Book Success!", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getAuthors() {
        return new ResponseEntity(authorRepository.findAll(), HttpStatus.OK);
    }
}
