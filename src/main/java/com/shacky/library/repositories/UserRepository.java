package com.shacky.library.repositories;

import com.shacky.library.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
    List<User> findTop5ByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String fname, String lname);

    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String first, String last);

    Optional<User> findByFirstNameAndMiddleNameAndLastName(String firstName, String middleName, String lastName);

}
