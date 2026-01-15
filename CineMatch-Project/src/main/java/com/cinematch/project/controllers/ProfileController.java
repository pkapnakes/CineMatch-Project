package com.cinematch.project.controllers;

import com.cinematch.project.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {

        // 🔐 Πάρε τον συνδεδεμένο χρήστη από τη session
        User user = (User) session.getAttribute("loggedUser");

        // ❌ Αν ΔΕΝ υπάρχει χρήστης → πίσω στο login
        if (user == null) {
            return "redirect:/login";
        }

        // 🛡️ Ασφάλεια: αν δεν υπάρχουν genres, βάλε άδεια λίστα
        if (user.getFavoriteGenres() == null) {
            user.setFavoriteGenres(List.of());
        }

        // ➜ Πέρασε τον user στο template
        model.addAttribute("user", user);

        return "profile";
    }
}
