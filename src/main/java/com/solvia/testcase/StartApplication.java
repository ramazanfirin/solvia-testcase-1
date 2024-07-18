package com.solvia.testcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.solvia.testcase.domain.Book;
import com.solvia.testcase.repository.BookRepository;

@SpringBootApplication
@EnableCaching
public class StartApplication {

    private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
    
    @Bean
    CommandLineRunner initDatabase(BookRepository repository) {
        return args -> {
            repository.save(new Book("Test1"));
            repository.save(new Book("Test2"));
            repository.save(new Book("Test3"));
        };
    }

}