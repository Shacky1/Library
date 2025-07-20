package com.shacky.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long totalBorrowed; // from query
    private String userType;
    private String clsRoom;

    // Constructor for JPQL query
    public UserDto(Long id, String firstName, String lastName, String email, Long totalBorrowed, String userType, String clsRoom) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.totalBorrowed = totalBorrowed;
        this.userType = userType;
        this.clsRoom = clsRoom;
    }

    public String getFullName() {
        if (firstName == null && lastName == null) return "";
        if (firstName == null) return lastName;
        if (lastName == null) return firstName;
        return firstName + " " + lastName;
    }
}
