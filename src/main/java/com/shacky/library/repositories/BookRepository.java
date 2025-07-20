package com.shacky.library.repositories;

import com.shacky.library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleAndSubjectAndAuthor(String title, String subject, String author);
    @Query("SELECT SUM(b.totalCopies) FROM Book b")
    Long sumTotalCopies();

}
