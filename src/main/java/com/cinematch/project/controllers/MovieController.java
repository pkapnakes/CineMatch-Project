package com.cinematch.project.controllers;

import com.cinematch.project.models.Movie;
import com.cinematch.project.services.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // GET /movies
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.findAll();
    }

    // GET /movies/{id}
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.findById(id);
    }

    // GET /movies/search?keyword=xxx
    @GetMapping("/search")
    public List<Movie> searchMovies(@RequestParam String keyword) {
        return movieService.search(keyword);
    }

    // GET /movies/trending
    @GetMapping("/trending")
    public List<Movie> getTrendingMovies() {
        return movieService.findTrending();
    }
}
