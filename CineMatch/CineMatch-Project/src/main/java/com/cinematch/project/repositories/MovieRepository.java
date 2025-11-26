package com.cinematch.project.repositories;

import com.cinematch.project.models.Movie;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository {
    List<Movie> findAll();
    Movie findById(Long id);
    List<Movie> search(String keyword);
    List<Movie> findTrending();
}
