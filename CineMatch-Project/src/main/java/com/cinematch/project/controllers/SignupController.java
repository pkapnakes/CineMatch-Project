package com.cinematch.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cinematch.project.models.User;
import com.cinematch.project.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignupController {

    private final UserRepository userRepository;

    public SignupController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

        // αποθήκευση στη βάση
        User user = new User(username, password, email, favoriteGenre);
        userRepository.save(user);

        System.out.println("New user created: " + username + " (" + email + "), fav=" + favoriteGenre);

        return "redirect:/login?signup=success";
    }
}

