package com.solvia.testcase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solvia.testcase.domain.Author;
import com.solvia.testcase.repository.AuthorRepository;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    
    public Author save(Author author) {
    	return authorRepository.save(author);
    }

    public List<Author> findAll() {
    	return authorRepository.findAll();
    }
}
