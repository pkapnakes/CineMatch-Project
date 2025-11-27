package com.cinematch.project.controllers;

import com.cinematch.project.models.User;
import com.cinematch.project.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

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
        // Έλεγχος αν οι κωδικοί ταιριάζουν
        if (!password.equals(confirmPassword)) {
            return "redirect:/signup?error=password_mismatch";
        }

        // Δημιουργία αντικειμένου User
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFavoriteGenre(favoriteGenre);

        // SAVE ΣΤΗ ΒΑΣΗ
        userRepository.save(user);

        return "redirect:/login?signup=success";
    }
}

