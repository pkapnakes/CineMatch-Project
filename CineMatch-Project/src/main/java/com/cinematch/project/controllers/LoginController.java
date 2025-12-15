package com.cinematch.project.controllers;

import com.cinematch.project.models.User;
import com.cinematch.project.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password) {

        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return "redirect:/home?username=" + username;
        }

        return "redirect:/login?error=true";
    }
}
