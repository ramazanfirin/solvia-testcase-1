package com.solvia.testcase.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.solvia.testcase.domain.Author;
import com.solvia.testcase.domain.Book;
import com.solvia.testcase.domain.enums.Genre;
import com.solvia.testcase.repository.AuthorRepository;
import com.solvia.testcase.repository.BookRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class BookServiceTest {
	
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookService bookService;
    
    @Autowired
    AuthorRepository authorRepository;
    
    @Autowired
    AuthorService authorService;
    
    @Container
    @ServiceConnection
    static MySQLContainer<?> postgres = new MySQLContainer<>(
            "mysql:8.0-debian"
    );
    
    Book book;
    Author author;
    
    @BeforeEach
    void testSetUp() {
    	bookRepository.deleteAll();
    	authorRepository.deleteAll();
    	init();
   }
    
    public void init() {
    	book = new Book();
    	book.setTitle("title");
    	book.setGenre(Genre.COMEDY);
    	
    	author = new Author();
    	author.setName("author");
    	author = authorService.save(author);
    	
    	book.setAuthor(author);
    	bookRepository.save(book);
    }
    
    @Test
    public void findAll() {
    	List<Book> list  = bookService.findAll();
    	assertEquals(1, list.size());
    }
    
    @Test
    public void findById() {
    	Optional<Book> response  = bookService.findById(book.getId());
    	assertEquals(response.get().getId(), book.getId());
    }
    
    @Test
    public void save() {
    	Book book2 = new Book();
    	book2.setTitle("test");
    	bookService.save(book2);
    	List<Book> list  = bookService.findAll();
    	assertEquals(2, list.size());
    }
    
    @Test
    public void saveError() {
    	Book book2 = new Book();
    	
    	Exception exception = assertThrows(Exception.class, () -> {
    		bookService.save(book2);
        });
    	

    }
    
    @Test
    public void delete() {
    	bookService.deleteById(book.getId());
    	List<Book> list  = bookService.findAll();
    	assertEquals(0, list.size());
    }
    
    @Test
    public void findByGenre() {
    	List<Book> list  = bookService.findByGenre(Genre.COMEDY);
    	assertEquals(1, list.size());
    }
    
    @Test
    public void findByAuthorId() {
    	List<Book> list  = bookService.findByAuthorId(author.getId());
    	assertEquals(1, list.size());
    }
}
