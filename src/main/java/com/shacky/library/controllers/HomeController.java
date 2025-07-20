package com.shacky.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping({"/"})
    public String home() {
        return "home"; // corresponds to home.html
    }

}
