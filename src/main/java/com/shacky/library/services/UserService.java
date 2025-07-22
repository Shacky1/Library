package com.shacky.library.services;

import com.shacky.library.dtos.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    // Create a new user
    UserDto createUser(UserDto userDto);

    // Update existing user by id
    UserDto updateUser(Long id, UserDto userDto);

    // Find user by id
    UserDto getUserById(Long id);

    // Get list of all users
    Page<UserDto> getAllUsers(Pageable pageable);

    // Delete user by id
    void deleteUser(Long id);

    boolean existsByFullName(String firstName, String middleName, String lastName);

    void importUsersFromExcel(MultipartFile file) throws Exception;


}
