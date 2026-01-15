package com.cinematch.project.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    // 📅 Ημερομηνία εγγραφής
    private LocalDate registrationDate;

    // 🎬 Πολλαπλά αγαπημένα είδη ταινιών
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_favorite_genres", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "genre")
    private List<String> favoriteGenres;

    // 🔹 Constructors
    public User() {}

    public User(String username, String password, String email, List<String> favoriteGenres) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.favoriteGenres = favoriteGenres;
        this.registrationDate = LocalDate.now(); // αυτόματα η σημερινή ημερομηνία
    }

    // ===== GETTERS =====
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public List<String> getFavoriteGenres() {
        return favoriteGenres;
    }

    // ===== SETTERS =====
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setFavoriteGenres(List<String> favoriteGenres) {
        this.favoriteGenres = favoriteGenres;
    }
}
