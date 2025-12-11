package com.cinematch.project.controllers;

import com.cinematch.project.models.User;
import com.cinematch.project.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam String username,
            @RequestParam String password
    ) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return "redirect:/login?error=user_not_found";
        }

        if (!user.getPassword().equals(password)) {
            return "redirect:/login?error=wrong_password";
        }

        // SUCCESS — redirect to HOME page
        return "redirect:/home?username=" + username;
    }
}
