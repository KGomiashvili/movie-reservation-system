package com.ol.springtask.demo.repository;

import com.ol.springtask.demo.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("SELECT m FROM Movie m WHERE " +
            "(:name IS NULL OR m.name LIKE %:name%) AND " +
            "(:description IS NULL OR m.description LIKE %:description%)")
    List<Movie> findMoviesWithFilters(@Param("name") String name, @Param("description") String description);

}
