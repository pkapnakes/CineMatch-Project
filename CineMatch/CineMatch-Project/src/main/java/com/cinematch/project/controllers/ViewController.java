package com.cinematch.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // φορτώνει templates/login.html
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // φορτώνει templates/home.html
    }
}
