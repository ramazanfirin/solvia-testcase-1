package com.solvia.testcase.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.solvia.testcase.domain.Book;
import com.solvia.testcase.domain.enums.Genre;
import com.solvia.testcase.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @Cacheable("books")
    public List<Book> findAll() {
        return bookService.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Book> findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED) 
    @PostMapping
    @CachePut(value = "books", key = "#book.isbn")
    public Book create(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping
    public Book update(@RequestBody Book book) {
        return bookService.update(book);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) 
    @DeleteMapping
    public void deleteById(@RequestParam Long id) {
        bookService.deleteById(id);
    }

    @GetMapping("/findByGenre")
    public List<Book> findByGenre(@PathVariable Genre genre) {
        return bookService.findByGenre(genre);
    }
    
    @GetMapping("/findByAuthorId")
    public List<Book> findByAuthorId(@PathVariable Long authorId) {
        return bookService.findByAuthorId(authorId);
    }


}

