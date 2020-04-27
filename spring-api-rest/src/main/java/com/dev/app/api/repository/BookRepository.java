package com.dev.app.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.app.api.model.Book;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long> {

}
