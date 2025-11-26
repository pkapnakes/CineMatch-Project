package com.cinematch.project.dto;

public class MovieDto {
    private String title;
    private String posterUrl;
    private String releaseDate;

    public MovieDto(String title, String posterUrl, String releaseDate) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
}
