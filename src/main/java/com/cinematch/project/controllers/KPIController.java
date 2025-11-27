package com.cinematch.project.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kpi")
public class KPIController {

    // GET /kpi/{movieId}
    @GetMapping("/{movieId}")
    public String getMovieKPIs(@PathVariable String movieId) {
        return "KPIs for movie: " + movieId;
    }

    // GET /kpi/actor/{actorId}
    @GetMapping("/actor/{actorId}")
    public String getActorKPIs(@PathVariable String actorId) {
        return "KPIs for actor: " + actorId;
    }
}
