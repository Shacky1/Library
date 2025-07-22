package com.shacky.library.services.impl;

import com.shacky.library.dtos.BookDto;
import com.shacky.library.entities.Book;
import com.shacky.library.repositories.BookRepository;
import com.shacky.library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Override
    public void importBooks(MultipartFile file) throws Exception {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                Book book = Book.builder()
                        .title(getCellValue(row.getCell(0)))
                        .subject(getCellValue(row.getCell(1)))
                        .gradeLevel(getCellValue(row.getCell(2)))
                        .author(getCellValue(row.getCell(3)))
                        .publicationYear(parseInt(row.getCell(4)))
                        .category(getCellValue(row.getCell(5)))
                        .bookNumber(getCellValue(row.getCell(6)))
                        .price(parseDouble(row.getCell(7)))
                        .build();

                bookRepository.save(book);
            }
        }
    }

    private String getCellValue(Cell cell) {
        return cell == null ? "" : cell.toString().trim();
    }

    private int parseInt(Cell cell) {
        try {
            return (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return 0;
        }
    }

    private double parseDouble(Cell cell) {
        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public Page<BookDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::convertToDto);
    }
}
