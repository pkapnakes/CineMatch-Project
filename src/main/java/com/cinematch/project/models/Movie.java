package com.cinematch.project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private Long id;
    private String title;
    private String description;
    private String genre;
    private String releaseDate;
    private double rating;
}
