package com.cinematch.project.controllers;

import com.cinematch.project.dto.MovieDto;
import com.cinematch.project.services.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tmdb")
public class TmdbController {

    @Autowired
    private TmdbService tmdbService;

    @GetMapping("/popular")
    public List<MovieDto> getPopularMovies() throws Exception {
        return tmdbService.getPopularMovies();
    }

    @GetMapping("/movie/{id}")
    public MovieDto getMovieDetails(@PathVariable Long id) throws Exception {
        return tmdbService.getMovieDetails(id);
    }

    @GetMapping("/search")
    public List<MovieDto> search(@RequestParam String query) throws Exception {
        return tmdbService.searchMovies(query);
    }
}
