package com.cinematch.project.controller;

import com.cinematch.project.dto.MovieDto;
import com.cinematch.project.service.TmdbService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TmdbController {

    private final TmdbService tmdbService;

    public TmdbController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @GetMapping("/tmdb/movies")
    public List<MovieDto> searchMovies(@RequestParam String query) throws Exception {
        return tmdbService.searchMovies(query);
    }
}
