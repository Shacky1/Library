package com.shacky.library.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String userType; //TEACHER or STUDENT
    private String clsRoom; // 1,2,3 and 4 it is required only if the user is a student

}
