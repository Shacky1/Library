package com.shacky.library.services;

import com.shacky.library.dtos.TransactionDto;

import java.util.List;

public interface TransactionService {

    // Create a new transaction (e.g. borrow a book)
    TransactionDto createTransaction(TransactionDto transactionDto);

    // Update an existing transaction (e.g. return a book)
    TransactionDto updateTransaction(Long id, TransactionDto transactionDto);

    // Find a transaction by its ID
    TransactionDto getTransactionById(Long id);

    List<TransactionDto> searchTransactionsByUser(String search);


    // Get all transactions
    List<TransactionDto> getAllTransactions();

    // Delete a transaction
    void deleteTransaction(Long id);

    // Count how many books are currently borrowed (status = "borrowed")
    long countBorrowedBooks();

    // Find all transactions for a specific user
    List<TransactionDto> getTransactionsByUserId(Long userId);

    // Find all transactions for a specific book
    List<TransactionDto> getTransactionsByBookId(Long bookId);

    // Return a book (sets status to "returned" and updates returnDate)
    TransactionDto returnBook(Long transactionId);
}
