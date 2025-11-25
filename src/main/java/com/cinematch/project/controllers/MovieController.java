package com.cinematch.project.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    // GET /movies/search
    @GetMapping("/search")
    public String searchMovies() {
        return "search movies placeholder";
    }

    // GET /movies/{id}
    @GetMapping("/{id}")
    public String getMovieById(@PathVariable String id) {
        return "movie id: " + id;
    }

    // GET /movies/trending
    @GetMapping("/trending")
    public String getTrendingMovies() {
        return "trending movies placeholder";
    }
}
