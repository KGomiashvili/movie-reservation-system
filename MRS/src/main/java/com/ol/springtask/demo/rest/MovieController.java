package com.ol.springtask.demo.rest;

import com.ol.springtask.demo.entity.Movie;
import com.ol.springtask.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        return movieService.getFilteredMovies(name, description);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Integer id) {
        return movieService.getMovieById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Movie createMovie(@RequestBody Movie movie) {
        movie.setId(0);
        return movieService.save(movie);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Integer id, @RequestBody Movie movie) {
        movie.setId(id);
        return movieService.save(movie);
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return "Deleted movie id: " + id;
    }
}
