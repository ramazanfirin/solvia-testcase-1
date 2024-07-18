package com.solvia.testcase.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.solvia.testcase.repository.AuthorRepository;
import com.solvia.testcase.repository.BookRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class AuthorServiceTest {
	
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorService authorService;
    
    @Container
    @ServiceConnection
    static MySQLContainer<?> postgres = new MySQLContainer<>(
            "mysql:8.0-debian"
    );
    
    Author author;
    
    @BeforeEach
    void testSetUp() {
    	author = new Author();
    	author.setName("name");
   }
   
    @Test
    public void save() {
    	authorService.save(author);
    	List<Author> list  = authorService.findAll();
    	assertEquals(1, list.size());
    }
    
}
