package com.shacky.library.services;

import com.shacky.library.dtos.BookDto;
import com.shacky.library.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> getAllBooks();
    Optional<BookDto> getBookById(Long id);
    BookDto saveBook(BookDto bookDto);
    void deleteBook(Long id);
}
