package com.shacky.library.services.impl;

import com.shacky.library.dtos.UserDto;
import com.shacky.library.entities.User;
import com.shacky.library.repositories.UserRepository;
import com.shacky.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Convert entity to DTO
    private UserDto toDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userType(user.getUserType())
                .clsRoom(user.getClsRoom())
                .build();
    }

    // Convert DTO to entity
    private User toEntity(UserDto dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUserType(dto.getUserType());
        user.setClsRoom(dto.getClsRoom());
        return user;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = toEntity(userDto);
        User saved = userRepository.save(user);
        return toDto(saved);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(userDto.getFirstName());
                    existingUser.setLastName(userDto.getLastName());
                    existingUser.setUserType(userDto.getUserType());
                    existingUser.setClsRoom(userDto.getClsRoom());
                    User updated = userRepository.save(existingUser);
                    return toDto(updated);
                })
                .orElse(null);  // or throw exception if preferred
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toDto)
                .orElse(null);  // or throw exception if preferred
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
