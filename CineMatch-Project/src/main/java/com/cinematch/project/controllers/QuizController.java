package com.cinematch.project.controllers;

import com.cinematch.project.models.QuizAttempt;
import com.cinematch.project.models.QuizQuestion;
import com.cinematch.project.models.User;
import com.cinematch.project.repositories.QuizAttemptRepository;
import com.cinematch.project.repositories.QuizQuestionRepository;
import com.cinematch.project.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class QuizController {

    private final UserRepository userRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizAttemptRepository quizAttemptRepository;

    public QuizController(UserRepository userRepository,
                          QuizQuestionRepository quizQuestionRepository,
                          QuizAttemptRepository quizAttemptRepository) {
        this.userRepository = userRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizAttemptRepository = quizAttemptRepository;
    }

    // ==========================================
    // GET: Εμφάνιση Quiz με 5 τυχαίες ερωτήσεις
    // ==========================================
    @GetMapping("/quiz")
    public String showQuiz(@RequestParam String username, Model model) {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "redirect:/login";
        }

        String genre = user.getFavoriteGenre();

        List<QuizQuestion> questions = quizQuestionRepository.findRandomFiveByGenre(genre);

        model.addAttribute("questions", questions);
        model.addAttribute("genre", genre);
        model.addAttribute("username", username);

        return "quiz";
    }

    // ==========================================
    // POST: Υποβολή Quiz + Υπολογισμός Score + Save στη βάση
    // ==========================================
    @PostMapping("/quiz/submit")
    public String submitQuiz(@RequestParam String username,
                             @RequestParam Map<String, String> allParams) {

        // 1) Βρίσκουμε τον χρήστη
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "redirect:/login";
        }

        // 2) Παίρνουμε genre
        String genre = user.getFavoriteGenre();

        // 3) Αφαιρούμε το username από τα params (μένουν μόνο οι απαντήσεις)
        allParams.remove("username");

        // 4) Υπολογισμός score
        int score = 0;

        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            String questionIdStr = entry.getKey(); // π.χ. "12"
            String selected = entry.getValue();    // π.χ. "A"

            Long qId;
            try {
                qId = Long.parseLong(questionIdStr);
            } catch (NumberFormatException e) {
                continue; // αγνόησε οτιδήποτε δεν είναι αριθμός
            }

            Optional<QuizQuestion> optQ = quizQuestionRepository.findById(qId);
            if (optQ.isPresent()) {
                QuizQuestion q = optQ.get();
                if (q.getCorrectOption() != null &&
                        q.getCorrectOption().equalsIgnoreCase(selected)) {
                    score++;
                }
            }
        }

        int totalQuestions = 5;

        // 5) Save attempt στη βάση
        QuizAttempt attempt = new QuizAttempt(user.getId(), genre, score, totalQuestions);
        quizAttemptRepository.save(attempt);

        // 6) Redirect στο αποτέλεσμα
        return "redirect:/quiz/result?username=" + username +
                "&score=" + score +
                "&total=" + totalQuestions;
    }

    // ==========================================
    // GET: Σελίδα αποτελέσματος
    // ==========================================
    @GetMapping("/quiz/result")
    public String showResult(@RequestParam String username,
                             @RequestParam int score,
                             @RequestParam int total,
                             Model model) {

        model.addAttribute("username", username);
        model.addAttribute("score", score);
        model.addAttribute("total", total);

        return "quiz-result";
    }
}
