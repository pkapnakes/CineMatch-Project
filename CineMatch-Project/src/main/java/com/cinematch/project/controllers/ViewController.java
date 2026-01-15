package com.cinematch.project.controllers;

import com.cinematch.project.models.QuizAttempt;
import com.cinematch.project.models.User;
import com.cinematch.project.repositories.QuizAttemptRepository;
import com.cinematch.project.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    private final UserRepository userRepository;
    private final QuizAttemptRepository quizAttemptRepository;

    public ViewController(UserRepository userRepository,
                          QuizAttemptRepository quizAttemptRepository) {
        this.userRepository = userRepository;
        this.quizAttemptRepository = quizAttemptRepository;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/home")
    public String homePage(@RequestParam(required = false) String username, Model model) {

        // Αν δεν έχουμε username, δεν ξέρουμε ποιος είναι -> πάμε login
        if (username == null || username.trim().isEmpty()) {
            return "redirect:/login";
        }

        // Βρίσκουμε τον user από τη βάση
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "redirect:/login";
        }

        // Παίρνουμε το πιο πρόσφατο quiz attempt (μπορεί να είναι null)
        QuizAttempt lastAttempt =
                quizAttemptRepository.findTopByUserIdOrderByCreatedAtDesc(user.getId());

        // Στέλνουμε δεδομένα στο home
        model.addAttribute("username", username);
        model.addAttribute("lastAttempt", lastAttempt);

        return "home";
    }
}
