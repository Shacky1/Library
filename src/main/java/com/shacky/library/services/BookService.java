package com.shacky.library.services;

import com.shacky.library.dtos.BookDto;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> getAllBooks();
    Optional<BookDto> getBookById(Long id);
    BookDto saveBook(BookDto bookDto);
    void deleteBook(Long id);
    void importBooks(MultipartFile file) throws Exception;


    Page<BookDto> getAllBooks(Pageable pageable);
}
