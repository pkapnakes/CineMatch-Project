package com.cinematch.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";   // templates/signup.html
    }

    @PostMapping("/signup")
    public String handleSignup(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam(required = false) String favoriteGenre
    ) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/signup?error=password_mismatch";
        }

        System.out.println("New user created: " + username + " (" + email + "), fav=" + favoriteGenre);

        return "redirect:/login?signup=success";
    }
}

