package com.ol.springtask.demo.service;

import com.ol.springtask.demo.entity.Movie;
import com.ol.springtask.demo.repository.MovieRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie getMovieById(Integer id) {
        Optional<Movie> result = movieRepository.findById(id);
        Hibernate.initialize(result.get().getSchedules());
        return result.orElseThrow(() -> new RuntimeException("Did not find Movie id: " + id));
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> getFilteredMovies(String name, String description) {
        List<Movie> allMovies = movieRepository.findMoviesWithFilters(name, description);
        allMovies.forEach(movie -> Hibernate.initialize(movie.getSchedules()));
        return allMovies;
    }
}
