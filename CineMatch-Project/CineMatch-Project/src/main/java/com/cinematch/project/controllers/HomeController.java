package com.cinematch.project.controllers;

import com.cinematch.project.dto.MovieDto;
import com.cinematch.project.services.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TmdbService tmdbService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/api/popular")
    @ResponseBody
    public List<MovieDto> getPopularMovies() throws Exception {
        return tmdbService.getPopularMovies();
    }

    // ❌ Removed duplicate /api/search mapping
}


