package com.shacky.library.services.impl;

import com.shacky.library.repositories.BookRepository;
import com.shacky.library.repositories.TransactionRepository;
import com.shacky.library.repositories.UserRepository;
import com.shacky.library.services.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public long countBooks() {
        Long total = bookRepository.sumTotalCopies();
        return total != null ? total : 0L;
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    public long countBorrowedBooks() {
        return transactionRepository.countByStatus("borrowed");
    }
}
