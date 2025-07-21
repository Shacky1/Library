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
    private String bookNumber;
    private String bookGradeLevel;
    private Long userId;
    private String userType;
    private String userFirstName;
    private String userMiddleName;
    private String userLastName;
    private String userEmail;
    private String status;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public String getFullName() {
        if (userFirstName == null &&userMiddleName==null && userLastName == null) {
            return "";
        }
        if (userFirstName == null) {
            return userMiddleName +" " + userLastName;
        }
        if (userMiddleName == null) {
            return userFirstName +" "+userLastName;
        }
        if (userLastName == null) {
            return userFirstName +" "+userMiddleName;
        }
        return userFirstName + " " +userMiddleName+" " + userLastName;
    }
}
