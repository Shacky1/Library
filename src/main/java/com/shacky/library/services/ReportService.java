package com.shacky.library.services;

import com.shacky.library.dtos.BookDto;
import com.shacky.library.dtos.TransactionDto;
import com.shacky.library.dtos.UserDto;

import java.util.List;

public interface ReportService {

    List<BookDto> getTopBorrowedBooks();

    List<UserDto> getTopActiveUsers();

    List<TransactionDto> getOverdueTransactions();

    long getTotalTransactionCount();



}