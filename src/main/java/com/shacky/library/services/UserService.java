package com.shacky.library.services;

import com.shacky.library.dtos.UserDto;
import java.util.List;

public interface UserService {

    // Create a new user
    UserDto createUser(UserDto userDto);

    // Update existing user by id
    UserDto updateUser(Long id, UserDto userDto);

    // Find user by id
    UserDto getUserById(Long id);

    // Get list of all users
    List<UserDto> getAllUsers();

    // Delete user by id
    void deleteUser(Long id);
}
