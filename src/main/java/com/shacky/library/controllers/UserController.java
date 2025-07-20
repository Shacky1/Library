package com.shacky.library.controllers;

import com.shacky.library.dtos.UserDto;
import com.shacky.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // List all users
    @GetMapping
    public String listUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("title", "Users");
        return "users/list";  // Thymeleaf template: users/list.html
    }

    // Show form to create new user
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("title", "Add New User");
        return "users/form";  // Thymeleaf template: users/form.html
    }

    // Handle POST to create new user
    @PostMapping
    public String createUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Add New User");
            return "users/form";
        }
        userService.createUser(userDto);
        return "redirect:/users";
    }

    // Show form to edit existing user
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserDto userDto = userService.getUserById(id);
        if (userDto == null) {
            return "redirect:/users";  // Or show 404 page
        }
        model.addAttribute("userDto", userDto);
        model.addAttribute("title", "Edit User");
        return "users/form";
    }

    // Handle POST to update existing user
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("userDto") UserDto userDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Edit User");
            return "users/form";
        }
        userService.updateUser(id, userDto);
        return "redirect:/users";
    }

    // Delete user by id
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    // Optional: View user details
    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        UserDto userDto = userService.getUserById(id);
        if (userDto == null) {
            return "redirect:/users";
        }
        model.addAttribute("userDto", userDto);
        model.addAttribute("title", "User Details");
        return "users/view";  // Thymeleaf template: users/view.html
    }
}
