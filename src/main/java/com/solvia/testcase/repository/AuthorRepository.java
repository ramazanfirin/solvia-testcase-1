package com.solvia.testcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solvia.testcase.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

   
}