package com.cinematch.project.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_attempts")
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Στον πίνακα έχεις user_id (BIGINT)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private int score;

    @Column(name = "total_questions", nullable = false)
    private int totalQuestions;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public QuizAttempt() {}

    public QuizAttempt(Long userId, String genre, int score, int totalQuestions) {
        this.userId = userId;
        this.genre = genre;
        this.score = score;
        this.totalQuestions = totalQuestions;
    }

    // ===== GETTERS =====
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getGenre() { return genre; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setScore(int score) { this.score = score; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
