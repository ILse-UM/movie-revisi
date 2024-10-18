package com.um.movie.model;

import java.time.LocalDate;

public class Movie {
    private String title;
    private String genre;
    private int duration;
    private LocalDate showingDate;
    private String image;
    private String Current;

    // Constructor
    public Movie(String title, String genre, int duration, LocalDate showingDate, String image) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.showingDate = showingDate;
        this.image = image;
        this.Current = "End Showing";
    }

    // Getters and Setters for Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getters and Setters for Genre
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Getters and Setters for Duration
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Getters and Setters for Showing Date
    public LocalDate getShowingDate() {
        return showingDate;
    }

    public void setShowingDate(LocalDate showingDate) {
        this.showingDate = showingDate;
    }

    // Getters and Setters for Image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCurrent() {
        return Current;
    }

    public void setCurrent(String current) {
        Current = current;
    }
}
