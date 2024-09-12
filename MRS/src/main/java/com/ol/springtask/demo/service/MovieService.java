package com.ol.springtask.demo.service;

import com.ol.springtask.demo.entity.Movie;

import java.util.List;

public interface MovieService {
    Movie getMovieById(Integer id);
    List<Movie> getAllMovies();
    Movie save(Movie movie);
    void deleteMovie(Integer id);

    List<Movie> getFilteredMovies(String name, String description);
}
