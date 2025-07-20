package com.shacky.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private String title;
    private String subject;
    private String gradeLevel;
    private String author;
    private int publicationYear;
    private String category;
    private double price;
    private int totalCopies;
    private int availableCopies;
    private Long borrowCount;  // from query

    // Constructor used by JPQL query (if you don't use builder)
    public BookDto(Long id, String title, String subject, String gradeLevel, String author, int publicationYear,
                   String category, double price, int totalCopies, int availableCopies, Long borrowCount) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.gradeLevel = gradeLevel;
        this.author = author;
        this.publicationYear = publicationYear;
        this.category = category;
        this.price = price;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.borrowCount = borrowCount;
    }
}

