package com.shacky.library.services.impl;

import com.shacky.library.dtos.BookDto;
import com.shacky.library.dtos.TransactionDto;
import com.shacky.library.dtos.UserDto;
import com.shacky.library.entities.Book;
import com.shacky.library.entities.Transaction;
import com.shacky.library.entities.User;
import com.shacky.library.repositories.BookRepository;
import com.shacky.library.repositories.TransactionRepository;
import com.shacky.library.repositories.UserRepository;
import com.shacky.library.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final TransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public List<BookDto> getTopBorrowedBooks() {
        // Count how many times each book ID appears across all transactions
        Map<Long, Long> borrowCountMap = transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(t -> t.getBook().getId(), Collectors.counting()));

        // Sort by borrow count descending and take top 5
        return borrowCountMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .map(entry -> {
                    Optional<Book> bookOpt = bookRepository.findById(entry.getKey());
                    return bookOpt.map(book -> BookDto.builder()
                            .id(book.getId())
                            .title(book.getTitle())
                            .author(book.getAuthor())
                            .subject(book.getSubject())
                            .gradeLevel(book.getGradeLevel())
                            .category(book.getCategory())
                            .price(book.getPrice())
                            .totalCopies(book.getTotalCopies())
                            .availableCopies(book.getAvailableCopies())
                            .borrowCount(entry.getValue())
                            .build()
                    ).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getTopActiveUsers() {
        // Count how many times each user ID appears across all transactions
        Map<Long, Long> userBorrowCountMap = transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(t -> t.getUser().getId(), Collectors.counting()));

        // Sort by total borrowed descending and take top 5
        return userBorrowCountMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .map(entry -> {
                    Optional<User> userOpt = userRepository.findById(entry.getKey());
                    return userOpt.map(user -> UserDto.builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            //.email(user.getEmail())
                            .userType(user.getUserType())
                            .clsRoom(user.getClsRoom())
                            .totalBorrowed(entry.getValue())
                            .build()
                    ).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getOverdueTransactions() {
        // Find transactions with returnDate before today and status not "RETURNED"
        List<Transaction> overdueTx = transactionRepository.findByReturnDateBeforeAndStatusNot(LocalDate.now(), "RETURNED");

        return overdueTx.stream()
                .map(tx -> TransactionDto.builder()
                        .id(tx.getId())
                        .bookId(tx.getBook().getId())
                        .bookTitle(tx.getBook().getTitle())
                        .bookAuthor(tx.getBook().getAuthor())
                        .bookSubject(tx.getBook().getSubject())
                        .userId(tx.getUser().getId())
                        .userFirstName(tx.getUser().getFirstName())
                        .userLastName(tx.getUser().getLastName())
                        .userType(tx.getUser().getUserType())
                        .status(tx.getStatus())
                        .borrowDate(tx.getBorrowDate())
                        .returnDate(tx.getReturnDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalTransactionCount() {
        return transactionRepository.count();
    }
}
