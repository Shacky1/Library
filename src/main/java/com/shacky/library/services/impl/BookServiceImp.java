package com.shacky.library.services.impl;

import com.shacky.library.dtos.BookDto;
import com.shacky.library.entities.Book;
import com.shacky.library.repositories.BookRepository;
import com.shacky.library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService {

    @Autowired
    private  BookRepository bookRepository;

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::convertToDto);
    }


    @Override
    public BookDto saveBook(BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        Book saved = bookRepository.save(book);
        return convertToDto(saved);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // DTO ↔ Entity helpers
    private BookDto convertToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .subject(book.getSubject())
                .author(book.getAuthor())
                .gradeLevel(book.getGradeLevel())
                .publicationYear(book.getPublicationYear())
                .price(book.getPrice())
                .bookNumber(book.getBookNumber())
                .category(book.getCategory())
                .build();
    }
    private Book convertToEntity(BookDto dto) {
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .subject(dto.getSubject())
                .author(dto.getAuthor())
                .gradeLevel(dto.getGradeLevel())
                .publicationYear(dto.getPublicationYear())
                .price(dto.getPrice())
                .bookNumber(dto.getBookNumber())
                .category(dto.getCategory())
                .build();
    }
}
