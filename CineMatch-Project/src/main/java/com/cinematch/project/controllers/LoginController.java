package com.cinematch.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class LoginController {

    private static final Map<String, String> USERS = Map.of(
            "sokratis", "12345",
            "hasan", "12345",
            "alex", "12345",
            "panos", "12345",
            "xrisa", "12345"
    );

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password) {

        String validPassword = USERS.get(username.toLowerCase());

        if (validPassword != null && validPassword.equals(password)) {
            return "redirect:/home?username=" + username;
        }

        return "redirect:/login?error=Invalid credentials";
    }
}
