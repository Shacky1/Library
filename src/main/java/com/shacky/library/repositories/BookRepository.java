package com.shacky.library.repositories;

import com.shacky.library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    Optional<Book> findByTitleAndGradeLevel(String title, String gradeLevel);



}
