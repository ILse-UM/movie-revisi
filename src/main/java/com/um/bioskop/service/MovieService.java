package com.um.bioskop.service;

import com.um.movie.model.Movie;
import com.um.movie.util.FileUtil;

import java.time.LocalDate;
import java.util.List;

public class MovieService {
    private List<Movie> movies;

    public MovieService() {
        movies = FileUtil.loadMoviesFromFile();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        FileUtil.saveMoviesToFile(movies);
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public void updateMovie(int id, String title, String genre, int duration, LocalDate showingDate) {
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                movie.setTitle(title);
                movie.setGenre(genre);
                movie.setDuration(duration);
                movie.setShowingDate(showingDate);
                break;
            }
        }
        FileUtil.saveMoviesToFile(movies);
    }

    public void deleteMovie(int id) {
        movies.removeIf(movie -> movie.getId() == id);
        FileUtil.saveMoviesToFile(movies);
    }
}
