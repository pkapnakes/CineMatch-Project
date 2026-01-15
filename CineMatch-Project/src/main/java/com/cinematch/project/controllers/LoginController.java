package com.cinematch.project.controllers;

import com.cinematch.project.models.User;
import com.cinematch.project.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
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
                              @RequestParam String password,
                              HttpSession session) {

        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {

            // ✅ ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ ΧΡΗΣΤΗ ΣΤΗ SESSION
            session.setAttribute("loggedUser", user);

            // ➜ Πήγαινε στο home
            return "redirect:/home";
        }

        return "redirect:/login?error=true";
    }
}
