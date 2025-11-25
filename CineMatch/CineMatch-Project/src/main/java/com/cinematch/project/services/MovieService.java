package com.cinematch.project.services;

import com.cinematch.project.models.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final List<Movie> movies = new ArrayList<>();

    public MovieService() {
        // Dummy data (MVP)
        movies.add(new Movie(1L, "Inception", "Dream within a dream", "Sci-Fi", "2010", 8.8));
        movies.add(new Movie(2L, "The Dark Knight", "Batman vs Joker", "Action", "2008", 9.0));
        movies.add(new Movie(3L, "Interstellar", "Space, time, love", "Sci-Fi", "2014", 8.6));
        movies.add(new Movie(4L, "Tenet", "Time inversion", "Sci-Fi", "2020", 7.5));
    }

    public List<Movie> findAll() {
        return movies;
    }

    public Movie findById(Long id) {
        return movies.stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Movie> search(String keyword) {
        return movies.stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Movie> findTrending() {
        return movies.stream()
                .sorted((m1, m2) -> Double.compare(m2.getRating(), m1.getRating()))
                .limit(3)
                .collect(Collectors.toList());
    }
}
