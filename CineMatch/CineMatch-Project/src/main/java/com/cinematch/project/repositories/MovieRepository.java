package com.cinematch.project.repositories;

import com.cinematch.project.models.Movie;
import java.util.List;

public interface MovieRepository {
    List<Movie> findAll();
    Movie findById(Long id);
    List<Movie> search(String keyword);
    List<Movie> findTrending();
}
