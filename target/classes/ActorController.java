package com.cinematch.project.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actors")
public class ActorController {

    // GET /actors/{id}
    @GetMapping("/{id}")
    public String getActorById(@PathVariable String id) {
        return "actor id: " + id;
    }

    // GET /actors/popular
    @GetMapping("/popular")
    public String getPopularActors() {
        return "popular actors placeholder";
    }
}
