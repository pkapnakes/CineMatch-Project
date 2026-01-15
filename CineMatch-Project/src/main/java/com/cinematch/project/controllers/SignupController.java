package com.cinematch.project.controllers;

import com.cinematch.project.models.User;
import com.cinematch.project.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
public class SignupController {

    private final UserRepository userRepository;

    public SignupController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ➤ Εμφάνιση της φόρμας εγγραφής
    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";   // templates/signup.html
    }

    // ➤ Επεξεργασία των δεδομένων της φόρμας
    @PostMapping("/signup")
    public String handleSignup(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam(required = false) List<String> favoriteGenre   // ✅ αλλαγή εδώ!
    ) {
        // Έλεγχος κωδικών
        if (!password.equals(confirmPassword)) {
            return "redirect:/signup?error=password_mismatch";
        }

        // Δημιουργία νέου χρήστη
        User user = new User(username, password, email, favoriteGenre);
        user.setRegistrationDate(LocalDate.now()); // ✅ ημερομηνία εγγραφής

        // Αποθήκευση στη βάση
        userRepository.save(user);

        System.out.println("✅ New user created: " + username +
                " (" + email + "), favorite genres=" + favoriteGenre);

        // Επιστροφή στη login σελίδα μετά την επιτυχή εγγραφή
        return "redirect:/login?signup=success";
    }
}
