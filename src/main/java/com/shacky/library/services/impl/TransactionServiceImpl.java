package com.shacky.library.services.impl;

import com.shacky.library.dtos.TransactionDto;
import com.shacky.library.entities.Book;
import com.shacky.library.entities.Transaction;
import com.shacky.library.entities.User;
import com.shacky.library.repositories.BookRepository;
import com.shacky.library.repositories.TransactionRepository;
import com.shacky.library.repositories.UserRepository;
import com.shacky.library.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private TransactionDto mapToDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .bookId(transaction.getBook().getId())
                .bookTitle(transaction.getBook().getTitle())
                .bookSubject(transaction.getBook().getSubject())
                .bookNumber(transaction.getBook().getBookNumber())
                .bookGradeLevel(transaction.getBook().getGradeLevel())
                .userId(transaction.getUser().getId())
                .userFirstName(transaction.getUser().getFirstName())
                .userMiddleName(transaction.getUser().getMiddleName())
                .userLastName(transaction.getUser().getLastName())
                .userEmail(transaction.getUser().getEmail())
                .status(transaction.getStatus())
                .borrowDate(transaction.getBorrowDate())
                .returnDate(transaction.getReturnDate())
                .build();
    }

    private Transaction mapToEntity(TransactionDto dto) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + dto.getBookId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));

        return Transaction.builder()
                .book(book)
                .user(user)
                .status(dto.getStatus())
                .borrowDate(dto.getBorrowDate())
                .returnDate(dto.getReturnDate())
                .build();
    }

    @Override
    public TransactionDto createTransaction(TransactionDto dto) {
        Transaction transaction = mapToEntity(dto);
        Book book = transaction.getBook();


        transaction.setStatus("borrowed");
        transaction.setBorrowDate(LocalDate.now());

        Transaction saved = transactionRepository.save(transaction);

        bookRepository.save(book);

        return mapToDto(saved);
    }

    @Override
    public TransactionDto updateTransaction(Long id, TransactionDto dto) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));

        transaction.setStatus(dto.getStatus());
        transaction.setBorrowDate(dto.getBorrowDate());
        transaction.setReturnDate(dto.getReturnDate());

        return mapToDto(transactionRepository.save(transaction));
    }

    @Override
    public TransactionDto getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public long countBorrowedBooks() {
        return transactionRepository.countByStatus("borrowed");
    }

    @Override
    public List<TransactionDto> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getTransactionsByBookId(Long bookId) {
        return transactionRepository.findByBookId(bookId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto returnBook(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));

        if (!"borrowed".equals(transaction.getStatus())) {
            throw new IllegalStateException("Book is not currently borrowed.");
        }

        transaction.setStatus("returned");
        transaction.setReturnDate(LocalDate.now());

        Book book = transaction.getBook();
        bookRepository.save(book);

        return mapToDto(transactionRepository.save(transaction));
    }

    @Override
    public List<TransactionDto> searchTransactionsByUser(String search) {
        return transactionRepository.searchByUserName(search)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // LIVE SEARCH: return list of { id, name } maps
    @Override
    public List<Map<String, Object>> searchBookTitles(String query) {
        List<Long> borrowedBookIds = transactionRepository.findByStatus("borrowed")
                .stream()
                .map(t -> t.getBook().getId())
                .collect(Collectors.toList());

        return bookRepository.findByTitleContainingIgnoreCase(query)
                .stream()
                .filter(book -> !borrowedBookIds.contains(book.getId())) //  Exclude borrowed books
                .map(book -> {
                    Map<String, Object> result = new java.util.HashMap<>();
                    result.put("id", book.getId());
                    result.put("name", book.getTitle());
                    result.put("subject", book.getSubject());
                    result.put("author", book.getAuthor());
                    result.put("grade", book.getGradeLevel());
                    result.put("number", book.getBookNumber());
                    return result;
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<Map<String, Object>> searchUserNames(String query) {
        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query)
                .stream()
                .map(user -> {
                    Map<String, Object> result = new java.util.HashMap<>();
                    result.put("id", user.getId());
                    result.put("name", user.getFirstName() + " " +user.getMiddleName()+ " " + user.getLastName());
                    return result;
                })
                .collect(Collectors.toList());
    }

}
