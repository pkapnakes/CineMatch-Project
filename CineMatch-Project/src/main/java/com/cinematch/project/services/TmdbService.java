package com.cinematch.project.service;

import com.cinematch.project.dto.MovieDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

@Service
public class TmdbService {

    private final WebClient webClient;

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    private final String imageBase = "https://image.tmdb.org/t/p/w500";

    public TmdbService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public List<MovieDto> searchMovies(String query) throws Exception {
        String response = webClient.get()
                .uri(baseUrl + "/search/movie?api_key=" + apiKey + "&query=" + query)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        List<MovieDto> movies = new ArrayList<>();

        for (JsonNode movie : root.get("results")) {
            String title = movie.get("title").asText();
            String releaseDate = movie.has("release_date") && !movie.get("release_date").isNull() ?
                    movie.get("release_date").asText() : "";
            String posterPath = movie.has("poster_path") && !movie.get("poster_path").isNull() ?
                    imageBase + movie.get("poster_path").asText() : null;

            movies.add(new MovieDto(title, posterPath, releaseDate));
        }

        return movies;
    }
}
