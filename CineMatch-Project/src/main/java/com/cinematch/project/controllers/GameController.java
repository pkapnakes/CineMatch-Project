package com.cinematch.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    // ==============================
    // ΣΕΛΙΔΑ ΠΑΙΧΝΙΔΙΟΥ
    // ==============================
    @GetMapping("/face-game")
    public String faceGamePage() {
        // Επιστρέφει το face-game.html
        return "face-game";
    }
}
