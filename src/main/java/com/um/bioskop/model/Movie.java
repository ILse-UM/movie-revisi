package com.um.bioskop.model;

import java.time.LocalDate;

public class Movie {
    // Attributes (using camelCase)
    private int id;
    private String title;
    private String genre;
    private Integer duration;
    private LocalDate showingDate;

    // Constructor
    public Movie(int id, String title, String genre, Integer duration, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.showingDate = releaseDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getShowingDate() {
        return showingDate;
    }

    public void setShowingDate(LocalDate showingDate) {
        this.showingDate = showingDate;
    }
}
