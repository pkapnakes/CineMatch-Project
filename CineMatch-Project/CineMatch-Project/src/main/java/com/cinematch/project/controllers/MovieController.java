package com.cinematch.project.controllers;

import com.cinematch.project.dto.MovieDto;
import com.cinematch.project.services.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private TmdbService tmdbService;

    @GetMapping("/api/trending")
    @ResponseBody
    public List<MovieDto> trending() throws Exception {
        return tmdbService.getPopularMovies();
    }

    @GetMapping("/movie/{id}")
    public String movieDetails(@PathVariable Long id, Model model) throws Exception {
        MovieDto movie = tmdbService.getMovieDetails(id);
        model.addAttribute("movie", movie);

        model.addAttribute("cast", tmdbService.getMovieCast(id));
        model.addAttribute("trailerKey", tmdbService.getMovieTrailer(id));
        model.addAttribute("similar", tmdbService.getSimilarMovies(id));

        // ⭐ NEW: Movie full details (runtime, genres, etc.)
        model.addAttribute("details", movie);

        // ⭐ NEW: Movie Reviews
        model.addAttribute("reviews", tmdbService.getMovieReviews(id));

        return "movie-details";
    }

    // ⬇⬇ Add this (API for FE JS search)
    @GetMapping("/api/search")
    @ResponseBody
    public List<MovieDto> apiSearch(@RequestParam("query") String query) throws Exception {
        return tmdbService.searchMovies(query);
    }

    // ⬇⬇ Add this (View for search results page)
    @GetMapping("/search")
    public String searchPage(@RequestParam("query") String query, Model model) {
        model.addAttribute("query", query);
        return "results";
    }
}
