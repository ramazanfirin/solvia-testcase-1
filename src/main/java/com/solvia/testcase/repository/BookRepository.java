package com.solvia.testcase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solvia.testcase.domain.Book;
import com.solvia.testcase.domain.enums.Genre;

import jakarta.persistence.LockModeType;


public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByGenre(@Param("genre") Genre genre);

    @Query("SELECT b FROM Book b WHERE b.author.id = :authorId")
    List<Book> findByAuthorId(@Param("authorId") Long authorId);

    @Lock(LockModeType.WRITE)
    Book save(Book book);
}