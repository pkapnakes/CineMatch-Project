package com.cinematch.project.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();   // 🔥 καθαρίζει τον χρήστη
        return "redirect:/login";
    }
}
