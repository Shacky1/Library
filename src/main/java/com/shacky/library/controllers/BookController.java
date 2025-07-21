package com.shacky.library.controllers;

import com.shacky.library.dtos.BookDto;
import com.shacky.library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 1. List all books
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("title", "Book List");
        model.addAttribute("books", bookService.getAllBooks());
        return "books/list";
    }

    // 2. Show form to add new book
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new BookDto());
        return "books/form";
    }

    // 3. Save new or updated book
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") BookDto bookDto,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            return "books/form";
        }


        bookService.saveBook(bookDto);
        return "redirect:/books";
    }

    // 4. Show form to edit book
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<BookDto> optionalBook = bookService.getBookById(id);
        if (optionalBook.isEmpty()) {
            return "redirect:/books";
        }
        model.addAttribute("book", optionalBook.get());
        return "books/form";
    }

    // 5. Delete a book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    // 6. View book details
    @GetMapping("/{id}")
    public String viewBookDetails(@PathVariable Long id, Model model) {
        Optional<BookDto> optionalBook = bookService.getBookById(id);
        if (optionalBook.isEmpty()) {
            return "redirect:/books";
        }
        model.addAttribute("book", optionalBook.get());
        return "books/details";
    }

    @PostMapping("/import")
    public String importBooksFromExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            bookService.importBooks(file);
            redirectAttributes.addFlashAttribute("success", "Books imported successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to import: " + e.getMessage());
        }
        return "redirect:/books";
    }

}
