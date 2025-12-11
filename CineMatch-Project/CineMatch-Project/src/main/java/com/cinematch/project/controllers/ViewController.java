package com.cinematch.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/home")
    public String homePage(@RequestParam(required = false) String username, Model model) {
        model.addAttribute("username", username);
        return "home";
    }
}
