package com.cinematch.project.services;

import com.cinematch.project.dto.MovieDto;
import com.cinematch.project.dto.CastDto;
import com.cinematch.project.dto.ReviewDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.cinematch.project.dto.ActorDto;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class TmdbService {

    private final String API_BASE = "https://api.themoviedb.org/3";
    private final String IMG_BASE = "https://image.tmdb.org/t/p/w500";
    private final String apikey = "a0a369592ca9d58024d1e0ec4b290b88";

    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    public List<MovieDto> getPopularMovies() throws Exception {
        String url = API_BASE + "/movie/popular?api_key=" + apikey + "&language=en-US&page=1";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode results = mapper.readTree(response.body()).get("results");

        List<MovieDto> movies = new ArrayList<>();
        for (JsonNode m : results) {
            MovieDto dto = new MovieDto();
            dto.setId(m.get("id").asLong());
            dto.setTitle(m.get("title").asText());
            dto.setOverview(m.get("overview").asText());
            dto.setPosterUrl(IMG_BASE + m.get("poster_path").asText());
            movies.add(dto);
        }
        return movies;
    }

    public MovieDto getMovieDetails(Long id) throws Exception {
        String url = API_BASE + "/movie/" + id + "?api_key=" + apikey + "&language=en-US";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode m = mapper.readTree(response.body());
        MovieDto dto = new MovieDto();

        dto.setId(m.get("id").asLong());
        dto.setTitle(m.get("title").asText());
        dto.setOverview(m.get("overview").asText());
        dto.setPosterUrl(IMG_BASE + m.get("poster_path").asText());

        // ⭐ ADDED FIELDS ⭐
        dto.setReleaseDate(m.get("release_date").asText());
        dto.setRating(m.get("vote_average").asDouble());
        dto.setRuntime(m.get("runtime").asInt());
        dto.setPopularity(m.get("popularity").asDouble());
        dto.setBudget(m.get("budget").asLong());
        dto.setRevenue(m.get("revenue").asLong());
        dto.setLanguage(m.get("original_language").asText());
        dto.setStatus(m.get("status").asText());

        // Convert genres array to comma-separated string
        StringBuilder genres = new StringBuilder();
        if (m.has("genres")) {
            for (JsonNode g : m.get("genres")) {
                if (genres.length() > 0) genres.append(", ");
                genres.append(g.get("name").asText());
            }
        }
        dto.setGenres(genres.toString());

        return dto;
    }

    public List<MovieDto> searchMovies(String query) throws Exception {
        String url = API_BASE + "/search/movie?api_key=" + apikey +
                "&query=" + query + "&language=en-US&page=1";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode results = mapper.readTree(response.body()).get("results");
        List<MovieDto> movies = new ArrayList<>();

        for (JsonNode m : results) {
            MovieDto dto = new MovieDto();
            dto.setId(m.get("id").asLong());
            dto.setTitle(m.get("title").asText());
            dto.setOverview(m.get("overview").asText());
            dto.setPosterUrl(IMG_BASE + m.get("poster_path").asText());
            movies.add(dto);
        }
        return movies;
    }

    public List<CastDto> getMovieCast(Long id) throws Exception {
        String url = API_BASE + "/movie/" + id + "/credits?api_key=" + apikey;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode cast = mapper.readTree(response.body()).get("cast");
        List<CastDto> castList = new ArrayList<>();

        for (JsonNode c : cast) {
            CastDto dto = new CastDto();
            dto.setName(c.get("name").asText());
            dto.setCharacter(c.get("character").asText());
            dto.setProfileUrl(IMG_BASE + c.get("profile_path").asText());
            castList.add(dto);
        }
        return castList;
    }

    public String getMovieTrailer(Long id) throws Exception {
        String url = API_BASE + "/movie/" + id + "/videos?api_key=" + apikey;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode videos = mapper.readTree(response.body()).get("results");

        for (JsonNode v : videos) {
            if (v.get("site").asText().equals("YouTube") &&
                    v.get("type").asText().equals("Trailer")) {
                return v.get("key").asText();
            }
        }
        return null;
    }

    public List<MovieDto> getSimilarMovies(Long id) throws Exception {
        String url = API_BASE + "/movie/" + id + "/similar?api_key=" + apikey + "&language=en-US&page=1";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode results = mapper.readTree(response.body()).get("results");
        List<MovieDto> movies = new ArrayList<>();

        for (JsonNode m : results) {
            MovieDto dto = new MovieDto();
            dto.setId(m.get("id").asLong());
            dto.setTitle(m.get("title").asText());
            dto.setOverview(m.get("overview").asText());
            dto.setPosterUrl(IMG_BASE + m.get("poster_path").asText());
            movies.add(dto);
        }
        return movies;
    }

    // ⭐ NEW METHOD — MOVIE REVIEWS ⭐
    public List<ReviewDto> getMovieReviews(Long id) throws Exception {
        String url = API_BASE + "/movie/" + id + "/reviews?api_key=" + apikey + "&language=en-US";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode results = mapper.readTree(response.body()).get("results");
        List<ReviewDto> reviews = new ArrayList<>();

        for (JsonNode r : results) {
            ReviewDto dto = new ReviewDto();
            dto.setAuthor(r.get("author").asText());
            dto.setContent(r.get("content").asText());

            JsonNode ratingNode = r.path("author_details").path("rating");
            dto.setRating(ratingNode.isNumber() ? ratingNode.asDouble() : null);

            reviews.add(dto);
        }

        return reviews;
    }
    // ===============================
// ⭐ POPULAR ACTORS (FOR FACE GAME)
// ===============================
    public List<ActorDto> getPopularActors() throws Exception {

        String url = API_BASE + "/person/popular?api_key=" + apikey + "&language=en-US&page=1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode results = mapper.readTree(response.body()).get("results");

        List<ActorDto> actors = new ArrayList<>();

        for (JsonNode a : results) {

            // skip actors without photo
            if (a.get("profile_path").isNull()) continue;

            ActorDto dto = new ActorDto();
            dto.setId(a.get("id").asLong());
            dto.setName(a.get("name").asText());
            dto.setImageUrl(IMG_BASE + a.get("profile_path").asText());

            actors.add(dto);

            // limit για performance (π.χ. 20 actors)
            if (actors.size() == 20) break;
        }

        return actors;
    }

}
