package com.shacky.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Long id;
    private Long bookId;
    private String bookAuthor;
    private String bookTitle;
    private String bookSubject;
    private Long userId;
    private String userType;
    private String userFirstName;
    private String userLastName;
    private String status;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public String getFullName() {
        if (userFirstName == null && userLastName == null) {
            return "";
        }
        if (userFirstName == null) {
            return userLastName;
        }
        if (userLastName == null) {
            return userFirstName;
        }
        return userFirstName + " " + userLastName;
    }
}
