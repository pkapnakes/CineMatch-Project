package com.cinematch.project.services;

import com.cinematch.project.models.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final List<Movie> movies = new ArrayList<>();

    public MovieService() {
        movies.add(new Movie(1L, "Inception", "Dream within a dream", "Sci-Fi", "2010", 8.8));
        movies.add(new Movie(2L, "Interstellar", "Space adventure", "Sci-Fi", "2014", 8.6));
        movies.add(new Movie(3L, "The Dark Knight", "Batman vs Joker", "Action", "2008", 9.0));
    }

    public Movie getMovie(Long id) {
        return movies.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public List<Movie> searchMovies(String keyword) {
        return movies.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public List<Movie> getTrendingMovies() {
        return movies.subList(0, Math.min(2, movies.size()));
    }
}
